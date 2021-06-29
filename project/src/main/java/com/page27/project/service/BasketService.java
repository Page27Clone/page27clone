package com.page27.project.service;

import com.page27.project.domain.Basket;
import com.page27.project.dto.ItemDto;

import java.util.List;

public interface BasketService {
    List<Basket> findAllBasketByMemberId(Long id);

    Basket findBasketById(Long id);

    void changeBasketItemQuantity(Long id, int changeQuantity);

    void deleteBasketById(Long id);

    List<ItemDto> basketListToPayment(String itemList);
}
