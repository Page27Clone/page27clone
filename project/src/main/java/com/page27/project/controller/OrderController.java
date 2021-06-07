package com.page27.project.controller;

import com.page27.project.domain.*;
import com.page27.project.dto.MemberDto;
import com.page27.project.dto.OrderDto;
import com.page27.project.dto.OrderInfo;
import com.page27.project.repository.OrderItemRepository;
import com.page27.project.repository.OrderRepository;
import com.page27.project.service.MemberService;
import com.page27.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @GetMapping("orders/list")
    public String showOrderList(Model model){
        List<Order> orderList = orderService.findAllOrders();
        OrderInfo orderInfo = new OrderInfo();
        List<OrderInfo> orderInfoList = new ArrayList<>();
        for(Order order : orderList){
            orderInfo.setName(order.getMember().getName());
            orderInfo.setOrderPrice(order.getTotalPrice());
//            orderInfo.setOrderStatus(order.getOrderStatus());
            orderInfo.setItemName(order.getOrderItemList().get(0).getItem().getItemName());

            orderInfoList.add(orderInfo);
        }
        model.addAttribute("orderInfoList",orderInfoList);

        return "admin/admin";
    }


    public String getOrderPage(Model model, @PageableDefault(size=4) Pageable pageable, SearchOrder searchOrder){

        if(StringUtils.isEmpty(searchOrder.getFirstdate()) && StringUtils.isEmpty(searchOrder.getLastdate()) && StringUtils.isEmpty(searchOrder.getSinput())){

            Page<OrderDto> orderBoards = orderRepository.searchAllOrder(pageable);
            int homeStartPage = Math.max(1,orderBoards.getPageable().getPageNumber() - 4);
            int homeEndPage = Math.min(orderBoards.getTotalPages(),orderBoards.getPageable().getPageNumber() + 4);
            model.addAttribute("startPage",homeStartPage);
            model.addAttribute("endPage",homeEndPage);
            model.addAttribute("orderList",orderBoards);

            return "admin/admin_order";
        }
        Page<OrderDto> orderBoards = orderRepository.searchAllOrderByCondition(searchOrder, pageable);

        int startPage = Math.max(1,orderBoards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(orderBoards.getTotalPages(),orderBoards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("orderList",orderBoards);

        model.addAttribute("firstDate", searchOrder.getFirstdate());
        model.addAttribute("lastDate",searchOrder.getLastdate());
        model.addAttribute("oMode",searchOrder.getOmode());
        model.addAttribute("sMode","buyer");
        model.addAttribute("sInput",searchOrder.getSinput());


        return "admin/admin_order";

    }

    @ResponseBody
    @PatchMapping("/admin/orderList1/{id}")
    public String orderStatusChangePage(@PathVariable Long id ,@RequestParam OrderStatus status){

        logger.info("id : " + id.toString());

        orderService.changeOrderStatus(id,status);

//        OrderItem findOrderItem = orderItemRepository.findById(id).get();
//        logger.info("new status : " + status);
//        findOrderItem.setOrderStatus(status);
//        logger.info("find orderItem price : " + findOrderItem.getOrderPrice());

        return "OrderItem 확인";
    }
}
