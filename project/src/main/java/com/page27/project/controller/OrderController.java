package com.page27.project.controller;

import com.page27.project.domain.Order;
import com.page27.project.dto.OrderInfo;
import com.page27.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("orders/list")
    public String showOrderList(Model model){
        List<Order> orderList = orderService.findAllOrders();
        OrderInfo orderInfo = new OrderInfo();
        List<OrderInfo> orderInfoList = new ArrayList<>();
        for(Order order : orderList){
            orderInfo.setName(order.getMember().getName());
            orderInfo.setOrderPrice(order.getTotalPrice());
            orderInfo.setOrderStatus(order.getOrderStatus());
            orderInfo.setItemName(order.getOrderItemList().get(0).getItem().getItemName());

            orderInfoList.add(orderInfo);
        }
        model.addAttribute("orderInfoList",orderInfoList);

        return "admin/admin";
    }
}
