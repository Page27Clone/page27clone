package com.page27.project.service;

import com.page27.project.domain.*;
import com.page27.project.repository.ItemRepository;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public List<Order> findAllOrders(){
        return orderRepository.findAll();
    }

    @Transactional
    public Long doOrder(Long memberId, Long itemId, int count){
        Optional<Member> findMember = memberRepository.findById(memberId);
        Member checkedFindMember = new Member();
        if(findMember.isPresent()){
            checkedFindMember =findMember.get();
        }

        Optional<Item> findItem = itemRepository.findById(itemId);
        Item checkedItem = new Item();
        if(findItem.isPresent()){
            checkedItem = findItem.get();
        }

        Delivery delivery = new Delivery();
        delivery.setAddress(checkedFindMember.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);
        //배송 정보 생성
        //delivery 객체를 만들 때 기본적 요소인 Member와 Address를 이용해서 만든다.

        OrderItem orderItem = OrderItem.createOrderItem(checkedItem, checkedItem.getPrice(), count);
        //주문 상품 생성
        Order order = Order.createOrder(checkedFindMember,delivery,orderItem);
        //주문 생성 -> 여기서 주문상품과 delivery 객체 이용
        // -> 즉 주문을 생성하기 이전에 주문 상품과 delivery가 만들어져야 한다.

        order.setTotalPrice(checkedItem.getPrice() * count);

        orderRepository.save(order);

        return order.getId();
    }// 주문 생성

    public void cancelOrder(Long orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        Order checkedOrder = new Order();

        if(order.isPresent()){
            checkedOrder = order.get();
        }
        checkedOrder.orderCancel();
    }
}
