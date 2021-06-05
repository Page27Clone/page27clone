package com.page27.project.service;

import com.page27.project.domain.*;
import com.page27.project.repository.ItemRepository;
import com.page27.project.repository.MemberRepository;
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
}
