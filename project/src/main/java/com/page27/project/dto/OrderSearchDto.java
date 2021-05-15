package com.page27.project.dto;

import com.page27.project.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSearchDto {
    private String memberName;
    private OrderStatus orderStatus;
}
