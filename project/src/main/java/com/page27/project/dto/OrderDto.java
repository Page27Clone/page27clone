package com.page27.project.dto;

import com.page27.project.domain.Item;
import com.page27.project.domain.Member;
import com.page27.project.domain.OrderItem;
import com.page27.project.domain.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderDto {
    private Long id;
    private String name;
    private String itemName;
    private LocalDate orderedAt;//이거 LocalDateTime이었는데 String으로 해보자 지금 LocalDateTime으로 하니까 order1.orderedAt이렇게 나온다. 안읽히는듯
    private String payment;
    private int orderPrice;
    private OrderStatus orderStatus;

    @QueryProjection
    public OrderDto(Long id, String name, String itemName, LocalDate orderedAt,String payment, int orderPrice,OrderStatus orderStatus) {
        this.id = id;
        this.name = name;
        this.itemName = itemName;
        this.orderedAt = orderedAt;
        this.payment = payment;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;

    }
}
