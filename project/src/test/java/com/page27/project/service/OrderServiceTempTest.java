package com.page27.project.service;

import com.page27.project.domain.*;
import com.page27.project.exception.NotEnoughStockException;
import com.page27.project.repository.ItemRepository;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTempTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private Member createTestMember(){
        Member member = new Member();
        member.setName("testMember1");
        member.setAddress(new Address("Seoul","Gangnam","13600"));
        memberRepository.save(member);

        return member;
    }

    private Item createTestItem(){
        Item item = new Item();
        item.setItemName("셔츠");
        item.setStockQuantity(10);
        item.setColor("Blue");
        item.setFabric("fabric");
        item.setPrice(100);

        itemRepository.save(item);

        return item;
    }

    @Rollback(value = false)
    @Test
    public void 상품_주문() throws Exception{
        Member member = createTestMember();
        Item item = createTestItem();
        int orderCount = 5;

//        Long testId = orderService.doOrder(member.getId(), item.getId(), orderCount);

        Order checkedOrder = new Order();

//        Optional<Order> getOrder = orderRepository.findById(testId);
//        if(getOrder.isPresent()){
//            checkedOrder = getOrder.get();
//        }
//        Assertions.assertEquals(checkedOrder.getOrderStatus(), OrderStatus.READY);
//        Assertions.assertEquals(checkedOrder.getOrderItemList().size(),1);
//        Assertions.assertEquals(checkedOrder.getTotalPrice(),5 * 100);
//        Assertions.assertEquals(item.getStockQuantity(),5);
    }

    @Test
    public void 상품주문_재고초과() throws Exception{
        Member member = createTestMember();
        Item item = createTestItem();
        int count = 20;
//        try{
//            Long testId = orderService.doOrder(member.getId(),item.getId(),count);
//        }catch(NotEnoughStockException e){
//            return;
//        }

        fail("재고 수량 부족 문제 발생");
    }

    @Test
    public void 주문_취소() throws Exception{
        Member member = createTestMember();
        Item item = createTestItem();
        int count = 5;

//        Long testId = orderService.doOrder(member.getId(),item.getId(),count);
//        orderService.cancelOrder(testId);

//        Optional<Order> testOrder = orderRepository.findById(testId);
//        Order checkedTestOrder = new Order();
//        if (testOrder.isPresent()){
//            checkedTestOrder = testOrder.get();


//        Assertions.assertEquals(checkedTestOrder.getOrderStatus(),OrderStatus.CANCEL);
//        Assertions.assertEquals(item.getStockQuantity(),10);

    }
}