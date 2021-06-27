package com.page27.project.service;

import com.page27.project.domain.Basket;
import com.page27.project.domain.Item;
import com.page27.project.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    Long save(Item item);
//    상품 저장하는 메소드

    Long changeItemStatusSoldOut(String idx, String color);
//    상품 상태 품절로 변경하는 메소드

    Long changeItemStatusOnSale(String idx, String color);
//    상품 상태 판매중으로 변경하는 메소드

    Long deleteItemById(String idx,String color);
//    Pk 이용해서 아이템 삭제하는 메소드

    ItemDetailDto getItemDetailDto(Long itemIdx);
//    상품 정보 찾는 메소드

    void moveItemToBasket(String loginId,Long itemIdx,String itemColor,int quantity);
//    상품을 장바구니에 담는 메소드

    ItemPageDto getItemPagingDto(Pageable pageable, String firstCategory, String secondCategory);
//    상품을 페이징해서 조회하는 메소드

    List<ItemDto> getAllItemInBasket(List<Basket> basketList);
//    장바구니에 있는 모든 아이템 조회

    List<ItemDto> itemToPayment(String itemList);
//    결제 페이지로 넘어가는 상품 준비

    ItemListToOrderDto itemToOrder(String orderItemInfo);
//    주문하기 위한 상품 데이터 준비

    List<Item> getMainCarouselItemList();
//    Main Carousel에 상품을 담아 반환하는 메소드

    List<WeeklyBestDto> getOuterWeeklyBestItem();
//    Outer 위클리 베스트 상품 반환하는 메소드

    List<WeeklyBestDto> getSleeveTopWeeklyBestItem();

    List<WeeklyBestDto> getShirtsWeeklyBestItem();

    List<WeeklyBestDto> getBottomWeeklyBestItem();

    List<WeeklyBestDto> getShoesWeeklyBestItem();

    List<WeeklyBestDto> getTopKnitWeeklyBestItem();

    List<WeeklyBestDto> getNewArrivalItem();
}
