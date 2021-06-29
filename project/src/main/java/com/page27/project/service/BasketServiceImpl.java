package com.page27.project.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.page27.project.domain.Basket;
import com.page27.project.dto.ItemDto;
import com.page27.project.exception.BasketNotFoundException;
import com.page27.project.repository.BasketRepository;
import com.page27.project.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<Basket> findAllBasketByMemberId(Long id) {
        List<Basket> basketList = basketRepository.findAllByMemberId(id);
        return basketList;
    }

    @Override
    public Basket findBasketById(Long id) {
        Basket findBasket = basketRepository.findById(id).orElseThrow(
                () -> new BasketNotFoundException("해당하는 장바구니를 찾을 수 없습니다")
        );
        return findBasket;
    }

    @Override
    @Transactional
    public void changeBasketItemQuantity(Long id, int changeQuantity) {
        Basket basket = basketRepository.findById(id).orElseThrow(
                () -> new BasketNotFoundException("해당하는 장바구니를 찾을 수 없습니다")
        );
        basket.setBasketCount(changeQuantity);
    }

    @Override
    @Transactional
    public void deleteBasketById(Long id) {
        basketRepository.deleteById(id);
    }

    @Override
    public List<ItemDto> basketListToPayment(String itemList) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(itemList);

        List<Long> basketIdList = new ArrayList<>();
        List<Integer> basketQuantityList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = (JsonObject) jsonArray.get(i);
            String id = object.get("id").getAsString();
            String quantity = object.get("quantity").getAsString();

            basketIdList.add(Long.parseLong(id));
            basketQuantityList.add(Integer.parseInt(quantity));
        }

        for (int i = 0; i < basketIdList.size(); i++) {
            Optional<Basket> byId = basketRepository.findById(basketIdList.get(i));
            Basket basket = byId.orElseThrow(
                    () -> new BasketNotFoundException("해당하는 장바구니가 존재하지 않습니다.")
            );
            Long itemId = basket.getItem().getId();

            itemDtoList.add(itemRepository.findAllItemInBasket(itemId));
        }

        return itemDtoList;
    }
}
