package com.page27.project.repository;

import com.page27.project.domain.SearchItem;
import com.page27.project.domain.SearchOrder;
import com.page27.project.dto.ItemDto;
import com.page27.project.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<OrderDto> searchAllOrder(Pageable pageable);
    Page<OrderDto> searchAllOrderByCondition(SearchOrder searchOrder, Pageable pageable);
}
