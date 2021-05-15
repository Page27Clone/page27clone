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

        OrderItem orderItem = OrderItem.createOrderItem(checkedItem, checkedItem.getPrice(), count);
        Order order = Order.createOrder(checkedFindMember,delivery,orderItem);

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
