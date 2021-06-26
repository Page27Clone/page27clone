package com.page27.project.service;

import com.page27.project.domain.OrderItem;
import com.page27.project.domain.OrderStatus;

public interface OrderItemService {
    OrderItem findOrderItemById(Long id);
    void chagneOrderStatus(Long id, OrderStatus orderStatus);
}
