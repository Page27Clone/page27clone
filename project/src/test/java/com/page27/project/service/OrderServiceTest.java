package com.page27.project.service;

import com.page27.project.domain.*;
import com.page27.project.exception.NotEnoughStockException;
import com.page27.project.repository.ItemRepository;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.MileageRepository;
import com.page27.project.repository.OrderRepository;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;


@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MileageRepository mileageRepository;

    private Member createTestMember(){
        Member member = new Member();
        member.setName("testMember1");
        member.setMemberAddress(new MemberAddress("Seoul","Gangnam","13600"));
        member.setVisitCount(5);


        Mileage mileage1 = new Mileage();
        mileage1.setMileageContent("회원가입 적립금");
        mileage1.setMileagePrice(1000);
        mileage1.setMember(member);

        Mileage mileage2 = new Mileage();
        mileage2.setMileageContent("1주년 기념 적립금");
        mileage2.setMileagePrice(2000);
        mileage2.setMember(member);

        memberRepository.save(member);

        return member;
    }

    private Member createTestMemberSecond(){
        Member member = new Member();
        member.setName("testMember2");
        member.setMemberAddress(new MemberAddress("Seoul","Gangnam","13600"));
        member.setVisitCount(7);
        memberRepository.save(member);

        return member;
    }

    private Item createTestItemFirst(){
        Item item = new Item();
        item.setItemName("셔츠");
        item.setStockQuantity(50);
        item.setColor("First Blue");
        item.setFabric("Second Fabric");
        item.setPrice(100);

        itemRepository.save(item);

        return item;
    }

    private Item createTestItemSecond(){
        Item item2 = new Item();
        item2.setItemName("바지");
        item2.setStockQuantity(50);
        item2.setColor("second White");
        item2.setFabric("second Fabric");
        item2.setPrice(200);

        itemRepository.save(item2);

        return item2;
    }

    private Item createTestItemThird(){
        Item item3 = new Item();
        item3.setItemName("양말");
        item3.setStockQuantity(50);
        item3.setColor("second White");
        item3.setFabric("second Fabric");
        item3.setPrice(300);

        itemRepository.save(item3);

        return item3;
    }


    @Transactional
    @Test
    @Rollback(value = false)
    public void 상품_주문() throws Exception{
        Member member = memberRepository.findByloginId("skyey94").get();
        Item itemFirst = itemRepository.findById(371L).get();

        List<Long> itemList = new ArrayList<>();
        itemList.add(itemFirst.getId());

        List<Integer> itemCountList = new ArrayList<>();
        itemCountList.add(1);

        orderService.doOrder(member.getId(), itemList, itemCountList);
        //doOrder 메소드 내에서 crateOrderItem 메소드가 실행됨
    }

    @Test
    public void 상품주문_재고초과() throws Exception{
//        Member member = createTestMember();
//        Item item = createTestItem();
//        int count = 20;
//        try{
//            Long testId = orderService.doOrder(member.getId(),item.getId(),count);
//        }catch(NotEnoughStockException e){
//            return;
//        }
//
//        fail("재고 수량 부족 문제 발생");
    }

    @Test
    public void 주문_취소(){
//        Member member = createTestMember();
//        Item item = createTestItem();
//        int count = 5;
//
//        Long testId = orderService.doOrder(member.getId(),item.getId(),count);
//        orderService.cancelOrder(testId);
//
//        Optional<Order> testOrder = orderRepository.findById(testId);
//        Order checkedTestOrder = new Order();
//        if (testOrder.isPresent()){
//            checkedTestOrder = testOrder.get();
//        }
//
////        Assertions.assertEquals(checkedTestOrder.getOrderStatus(),OrderStatus.CANCEL);
//        Assertions.assertEquals(item.getStockQuantity(),10);

    }
}