package com.page27.project.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 관계 메소드
    public void setItem(Item item){
        this.item = item;
        item.getOrderItemList().add(this);
    }

    // 생성 메소드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice * count);
        orderItem.setCount(count);

        item.minusStockQuantity(count);

        return orderItem;
    }

    /* 비즈니스 로직 */

    public void itemCancel(){
        this.getItem().plusStockQuantity(count);
    }// 취소하면서 재고 복구

    public int getCalPrice(){
        return this.getOrderPrice() * this.getCount();
    }// 전체 가격조회
}
