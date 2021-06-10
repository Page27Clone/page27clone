package com.page27.project.service;

import com.page27.project.domain.*;
import com.page27.project.dto.MyPageOrderStatusDto;
import com.page27.project.repository.ItemRepository;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.OrderItemRepository;
import com.page27.project.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Long doOrder(Long memberId, List<Long> itemId, List<Integer> count) {
        Optional<Member> findMember = memberRepository.findById(memberId);

        Member checkedFindMember = new Member();
        if (findMember.isPresent()) {
            checkedFindMember = findMember.get();
        }

        Delivery delivery = new Delivery();
        delivery.setAddress(checkedFindMember.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);

        //배송 정보 생성
        //delivery 객체를 만들 때 기본적 요소인 Member와 Address를 이용해서 만든다.

        List<OrderItem> checkedTestOrderItem = new ArrayList<>();

        for(int i = 0; i< itemId.size();i++){
            Item checkedItem = itemRepository.findById(itemId.get(i)).get();
            OrderItem testOrderItem = OrderItem.createOrderItem(checkedItem,checkedItem.getPrice(),count.get(i));
            testOrderItem.setOrderStatus(OrderStatus.PAYWAITING);
            checkedTestOrderItem.add(testOrderItem);
        }

        Order order = Order.createOrder(checkedFindMember,delivery,checkedTestOrderItem);

        orderRepository.save(order);

        return order.getId();
    }// 주문 생성

    public void cancelOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        Order checkedOrder = new Order();

        if (order.isPresent()) {
            checkedOrder = order.get();
        }
        checkedOrder.orderCancel();
    }

    @Transactional
    public Long changeOrderStatus(Long orderItemId,OrderStatus orderStatus){
        Optional<OrderItem> findOrderItem = orderItemRepository.findById(orderItemId);
        OrderItem checkedOrderItem= new OrderItem();
        if(findOrderItem.isPresent()){
            checkedOrderItem = findOrderItem.get();
        }
        checkedOrderItem.setOrderStatus(orderStatus);

        return checkedOrderItem.getId();
    }

    public MyPageOrderStatusDto showOrderStatus(String loginId){
        int payWaitingNum = 0;
        int payCompleteNum = 0;
        int preShipNum = 0;
        int inShipNum = 0;
        int completeShip = 0;
        int orderCancelNum = 0;
        int orderChangeNum = 0;
        int orderRefundNum = 0;

        Member member = memberRepository.findByloginId(loginId).get();

        for(int i = 0;i< member.getOrderList().size();i++){
            Order order = member.getOrderList().get(i);
            for(int j= 0;j< order.getOrderItemList().size();j++){
                OrderItem orderItem = order.getOrderItemList().get(j);
                if(orderItem.getOrderStatus().equals(OrderStatus.PAYWAITING)) payWaitingNum += 1;
                if(orderItem.getOrderStatus().equals(OrderStatus.PAYCOMPLETE)) payCompleteNum += 1;
                if(orderItem.getOrderStatus().equals(OrderStatus.INSHIP)) inShipNum += 1;
                if(orderItem.getOrderStatus().equals(OrderStatus.PRESHIP)) preShipNum += 1;
                if(orderItem.getOrderStatus().equals(OrderStatus.COMPLETESHIP)) completeShip += 1;
                if (orderItem.getOrderStatus().equals(OrderStatus.ORDERCANCEL)) orderCancelNum += 1;
                if (orderItem.getOrderStatus().equals(OrderStatus.ORDERCHANGE)) orderChangeNum += 1;
                if(orderItem.getOrderStatus().equals(OrderStatus.ORDERREFUND)) orderRefundNum += 1;
            }
        }

        MyPageOrderStatusDto myPageOrderStatusDto = new MyPageOrderStatusDto(payWaitingNum,preShipNum,inShipNum,completeShip,orderCancelNum,orderChangeNum,orderRefundNum);

        return myPageOrderStatusDto;
    }
}
