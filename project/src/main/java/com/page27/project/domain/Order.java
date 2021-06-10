package com.page27.project.domain;

import com.page27.project.exception.DeliveryException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    private LocalDate orderedAt;

    private String payment;

    private int totalPrice;

    //연관관계 메소드
    public void setMember(Member member){
        this.member = member;
        member.getOrderList().add(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    // 생성 메소드
    public static Order createOrder(Member member, Delivery delivery , List<OrderItem> orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
//        order.setOrderStatus(OrderStatus.READY);
        order.setOrderedAt(LocalDate.now());
        order.setPayment("카드결제");
        order.setTotalPrice(order.getCalTotalPrice());

        return order;
    }

    /* 비즈니스 로직 */

    public void orderCancel(){
        if(this.delivery.getDeliveryStatus() == DeliveryStatus.COMPLETE){
            throw new DeliveryException("이미 배송완료된 상품입니다.");
        }else{
//            this.setOrderStatus(OrderStatus.CANCEL);
            this.delivery.setDeliveryStatus(DeliveryStatus.CANCEL);

            for(OrderItem orderItem : orderItemList){
                orderItem.itemCancel();
            }
        }
    }// 주문 취소

    public int getCalTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItemList){
            totalPrice += orderItem.getCalPrice();
        }
        return totalPrice;
    }// 전체 가격 조회
}
