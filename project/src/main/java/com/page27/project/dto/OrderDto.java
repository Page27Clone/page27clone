package com.page27.project.dto;

import com.page27.project.domain.Item;
import com.page27.project.domain.Member;
import com.page27.project.domain.OrderItem;
import com.page27.project.domain.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long orderItemId;
    private String name;
    private String itemName;
    private LocalDate orderedAt;
    private String payment;
    private int orderPrice;
    private OrderStatus orderStatus;

    @QueryProjection
    public OrderDto(Long id,Long orderItemId, String name, String itemName, LocalDate orderedAt,String payment, int orderPrice,OrderStatus orderStatus) {
        this.id = id;
        this.orderItemId = orderItemId;
        this.name = name;
        this.itemName = itemName;
        this.orderedAt = orderedAt;
        this.payment = payment;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;

    }
}
