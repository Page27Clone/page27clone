package com.page27.project.dto;

import com.page27.project.domain.Member;
import com.page27.project.domain.Order;
import com.page27.project.domain.OrderItem;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private String name;
    private LocalDateTime orderedAt;
    private String payment;
    private List<OrderItem> orderItemList;
    private int totalPrice;

    @QueryProjection
    public OrderDto(String name, LocalDateTime orderedAt, String payment, List<OrderItem> orderItemList, int totalPrice) {
        this.name = name;
        this.orderedAt = orderedAt;
        this.payment = payment;
        this.orderItemList = orderItemList;
        this.totalPrice = totalPrice;
    }
}
