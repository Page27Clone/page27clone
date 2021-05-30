package com.page27.project.repository;

import com.page27.project.domain.*;
import com.page27.project.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired OrderRepository orderRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired OrderService orderService;

    private Member createTestMember(){
        Member member = new Member();
        member.setName("testMember");
        member.setAddress(new Address("경기도","수내로","13600"));
        memberRepository.save(member);

        return member;
    }

    private Item createTestItem(){
        Item item = new Item();
        item.setItemName("testItem");
        item.setStockQuantity(50);
        item.setPrice(100);

        itemRepository.save(item);

        return item;
    }

    @Test
    @Rollback(value = false)
    public void 주문목록_조회() throws Exception{
        Member member = createTestMember();
        Item item = createTestItem();

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.COMPLETE);

        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),10);
        Order order = Order.createOrder(member,delivery,orderItem);

        orderRepository.save(order);

        orderService.doOrder(member.getId(),item.getId(),10);

    }
}