package com.page27.project.service;

import com.page27.project.domain.*;
import com.page27.project.dto.*;
import com.page27.project.exception.LoginIdNotFoundException;
import com.page27.project.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final MileageRepository mileageRepository;

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    @Override
    public Long doOrder(Long memberId, List<Long> itemId, List<Integer> count, PaymentAddressDto paymentAddressDto, PaymentPriceDto paymentPriceDto) {
        Optional<Member> findMember = memberRepository.findById(memberId);

        Member checkedFindMember = new Member();
        if (findMember.isPresent()) {
            checkedFindMember = findMember.get();
        }

        Delivery delivery = new Delivery();

        MemberAddress memberAddress = new MemberAddress();
        memberAddress.setCity(paymentAddressDto.getCity());
        memberAddress.setStreet(paymentAddressDto.getStreet());
        memberAddress.setZipcode(paymentAddressDto.getZipcode());
        delivery.setMemberAddress(memberAddress);
        delivery.setDeliveryStatus(DeliveryStatus.READY);

        //배송 정보 생성
        //delivery 객체를 만들 때 기본적 요소인 Member와 MemberAddress를 이용해서 만든다.

        List<OrderItem> checkedTestOrderItem = new ArrayList<>();

        for(int i = 0; i< itemId.size();i++){
            Item checkedItem = itemRepository.findById(itemId.get(i)).get();
            OrderItem testOrderItem = OrderItem.createOrderItem(checkedItem,checkedItem.getPrice(),count.get(i));
            testOrderItem.setOrderStatus(OrderStatus.PAYWAITING);
            checkedTestOrderItem.add(testOrderItem);
        }

        Order order = Order.createOrder(checkedFindMember,delivery,checkedTestOrderItem);
        order.setUsedMileagePrice(paymentPriceDto.getUsed_mileage());

        Mileage mileage = new Mileage();
        mileage.setMileageContent("구매 적립금");
        mileage.setMileagePrice(paymentPriceDto.getTobepaid_price() / 100);
        mileage.setMember(checkedFindMember);

        mileageRepository.save(mileage);
        orderRepository.save(order);
        return order.getId();
    }// 주문 생성

    @Override
    public void cancelOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        Order checkedOrder = new Order();

        if (order.isPresent()) {
            checkedOrder = order.get();
        }
        checkedOrder.orderCancel();
    }

    @Transactional
    @Override
    public Long changeOrderStatus(Long orderItemId,OrderStatus orderStatus){
        Optional<OrderItem> findOrderItem = orderItemRepository.findById(orderItemId);
        OrderItem checkedOrderItem= new OrderItem();
        if(findOrderItem.isPresent()){
            checkedOrderItem = findOrderItem.get();
        }
        checkedOrderItem.setOrderStatus(orderStatus);

        return checkedOrderItem.getId();
    }

    @Override
    public MyPageOrderStatusDto showOrderStatus(String loginId){
        int payWaitingNum = 0;
        int preShipNum = 0;
        int inShipNum = 0;
        int completeShip = 0;
        int orderCancelNum = 0;
        int orderChangeNum = 0;
        int orderRefundNum = 0;

        Member member = memberRepository.findByloginId(loginId).orElseThrow(
                ()-> new LoginIdNotFoundException("해당하는 회원을 찾을 수 없습니다")
        );

        for(int i = 0;i< member.getOrderList().size();i++){
            Order order = member.getOrderList().get(i);
            for(int j= 0;j< order.getOrderItemList().size();j++){
                OrderItem orderItem = order.getOrderItemList().get(j);
                if(orderItem.getOrderStatus().equals(OrderStatus.PAYWAITING)) payWaitingNum += 1;
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

    @Override
    public OrderMainPageDto getOrderPagingDto(SearchOrder searchOrder, Pageable pageable, String loginId){
        OrderMainPageDto orderPageDto = new OrderMainPageDto();
        Page<MainPageOrderDto> mainPageOrderBoards = null;

        if(StringUtils.isEmpty(searchOrder.getFirstdate()) && StringUtils.isEmpty(searchOrder.getLastdate())) {
            mainPageOrderBoards = orderRepository.mainPageSearchAllOrder(pageable, loginId);
        }
        else {
            mainPageOrderBoards = orderRepository.mainPageSearchAllOrderByCondition(searchOrder, pageable, loginId);
        }
        int homeStartPage = Math.max(1, mainPageOrderBoards.getPageable().getPageNumber() - 4);
        int homeEndPage = Math.min(mainPageOrderBoards.getTotalPages(), mainPageOrderBoards.getPageable().getPageNumber() + 4);

        orderPageDto.setMainPageOrderBoards(mainPageOrderBoards);
        orderPageDto.setHomeStartPage(homeStartPage);
        orderPageDto.setHomeEndPage(homeEndPage);

        return orderPageDto;
    }

    @Override
    public OrderPageDto findAllOrderByPaging(Pageable pageable) {
        OrderPageDto orderPageDto = new OrderPageDto();

        Page<OrderDto> orderBoards = orderRepository.searchAllOrder(pageable);
        int homeStartPage = Math.max(1,orderBoards.getPageable().getPageNumber() - 4);
        int homeEndPage = Math.min(orderBoards.getTotalPages(),orderBoards.getPageable().getPageNumber() + 4);

        orderPageDto.setOrderBoards(orderBoards);
        orderPageDto.setHomeStartPage(homeStartPage);
        orderPageDto.setHomeEndPage(homeEndPage);

        return orderPageDto;
    }

    @Override
    public OrderPageDto findAllOrderByConditionByPaging(SearchOrder searchOrder, Pageable pageable) {
        OrderPageDto orderPageDto = new OrderPageDto();

        Page<OrderDto> orderBoards = orderRepository.searchAllOrderByCondition(searchOrder, pageable);
        int startPage = Math.max(1,orderBoards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(orderBoards.getTotalPages(),orderBoards.getPageable().getPageNumber() + 4);

        orderPageDto.setOrderBoards(orderBoards);
        orderPageDto.setHomeStartPage(startPage);
        orderPageDto.setHomeEndPage(endPage);

        return orderPageDto;
    }

    @Override
    public Page<OrderDto> findAllOrder(Pageable pageable) {
        return orderRepository.searchAllOrder(pageable);
    }
//    order 페이지의 페이징 관련 메소드
}
