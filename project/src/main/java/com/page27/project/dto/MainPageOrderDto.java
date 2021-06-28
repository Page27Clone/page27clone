package com.page27.project.dto;

import com.page27.project.domain.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

@Getter
@Setter
public class MainPageOrderDto {
    private Long id;
    private Long orderItemId;
    private Long itemId;
    private String name;
    private String itemName;
    private LocalDate orderedAt;
    private OrderStatus orderStatus;
    private int price;
    private int count;
    private String url;
    private String color;

    @QueryProjection
    public MainPageOrderDto(Long id, Long orderItemId, Long itemId, String name, String itemName, LocalDate orderedAt, OrderStatus orderStatus, int price, int count, String url, String color) {
        this.id = id;
        this.orderItemId = orderItemId;
        this.itemId = itemId;
        this.name = name;
        this.itemName = itemName;
        this.orderedAt = orderedAt;
        this.orderStatus = orderStatus;
        this.price = price;
        this.count = count;
        this.url = url;
        this.color = color;
    }
}
