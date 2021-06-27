package com.page27.project.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.page27.project.domain.Basket;
import com.page27.project.domain.Item;
import com.page27.project.domain.Member;
import com.page27.project.domain.SearchItem;
import com.page27.project.dto.*;
import com.page27.project.repository.BasketRepository;
import com.page27.project.repository.ItemRepository;
import com.page27.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;

    @Transactional
    @Override
    public Long saveItem(Item item){
        itemRepository.save(item);

        return item.getId();
    }// 상품 저장

    @Transactional
    @Override
    public Long changeItemStatusSoldOut(String idx, String color){
        Long itemIdx = Long.parseLong(idx);
        List<Item> findItem = itemRepository.findAllByItemIdxAndColor(itemIdx,color);
        for(Item changeItem : findItem){
            changeItem.setSaleStatus("soldout");
        }
        return itemIdx;
    }

    @Transactional
    @Override
    public Long changeItemStatusOnSale(String idx, String color){
        Long itemIdx = Long.parseLong(idx);
        List<Item> findItem = itemRepository.findAllByItemIdxAndColor(itemIdx,color);
        for(Item changeItem : findItem){
            changeItem.setSaleStatus("onsale");
        }
        return itemIdx;
    }

    @Transactional
    @Override
    public Long deleteItemById(String idx,String color){
        Long itemIdx = Long.parseLong(idx);
        List<Item> findItem = itemRepository.findAllByItemIdxAndColor(itemIdx,color);
        for(Item changeItem : findItem){
            itemRepository.deleteById(changeItem.getId());
        }
        return itemIdx;
    }

    @Override
    public ItemDetailDto getItemDetailDto(Long itemIdx){

        List<Item> itemListByItemIdx = itemRepository.findAllByItemIdx(itemIdx);
//        모든 색깔 다 나옴 근데 사진은 여기서 뽑으면 중복으로 나옴

//        SELECT * FROM ITEM WHERE REP = TRUE AND ITEM_IDX = 1;
        List<Item> findItemByitemIdxAndRep = itemRepository.findAllByItemIdxAndRep(itemIdx, true);
        String imgMainUrl = findItemByitemIdxAndRep.get(0).getImgUrl();
//        메인 이미지 완료
        List<String> getColorList = new ArrayList<>();
        for(int i = 0; i< findItemByitemIdxAndRep.size();i++){
            getColorList.add(findItemByitemIdxAndRep.get(i).getColor());
        }
//        색깔 리스트 완료

        Item topItemByItemIdxAndRep = itemRepository.findTopByItemIdxAndRep(itemIdx, true);
        String itemName = topItemByItemIdxAndRep.getItemName();
//        아이템 이름 완료
        int price = topItemByItemIdxAndRep.getPrice();
//        아이템 가격 완료
        String itemInfo = topItemByItemIdxAndRep.getItemInfo();
//        아이템 정보 완료
        String itemFabric = topItemByItemIdxAndRep.getFabric();
//        아이템 원단 완료
        String itemModel = topItemByItemIdxAndRep.getModel();
//        아이템 모델 완료
        double mileage = topItemByItemIdxAndRep.getPrice() * 0.01;
//        아이템 마일리지 완료

        List<Long> idList = new ArrayList<>();
        for(int i = 0; i< itemListByItemIdx.size();i++){
            idList.add(itemListByItemIdx.get(i).getId());
        }
//        아이템 고유 번호 완료

        List<String> imgUrlList = new ArrayList<>();
        List<Item> itemByItemIdxAndColor = itemRepository.findAllByItemIdxAndColor(itemIdx, topItemByItemIdxAndRep.getColor());

        for(int i = 0; i< itemByItemIdxAndColor.size();i++){
            imgUrlList.add(itemByItemIdxAndColor.get(i).getImgUrl());
        }
//        아이템 사진리스트 완료

        ItemDetailDto itemDetailDto = new ItemDetailDto();
        itemDetailDto.setImgMainUrl(imgMainUrl);
        itemDetailDto.setColorList(getColorList);
        itemDetailDto.setItemName(itemName);
        itemDetailDto.setPrice(price);
        itemDetailDto.setItemInfo(itemInfo);
        itemDetailDto.setFabric(itemFabric);
        itemDetailDto.setModel(itemModel);
        itemDetailDto.setItemIdx(itemIdx);
        itemDetailDto.setItemId(idList);
        itemDetailDto.setMileage(mileage);
        itemDetailDto.setImgUrlList(imgUrlList);

//        대표 사진
//        아이템 색깔리스트
//        아이템 이름
//        아이템 가격
//        아이템 정보
//        아이템 원단
//        아이템 모델
//        아이템 번호
//        아이템 고유번호
//        아이템 마일리지
//        아이템 사진리스트

        return itemDetailDto;
    }
    
    @Override
    public void moveItemToBasket(String loginId,Long itemIdx,String itemColor,int quantity){

        Basket basket = new Basket();
        Member findMember = memberRepository.findByloginId(loginId).get();
        Item findItem = itemRepository.findByItemIdxAndColorAndRep(itemIdx, itemColor, true);

        basket.setMember(findMember);
        basket.setBasketCount(quantity);
        basket.setItem(findItem);

        basketRepository.save(basket);
    }

    @Override
    public ItemPageDto getItemPagingDtoByCategory(Pageable pageable, String firstCategory, String secondCategory) {
        ItemPageDto itemPageDto = new ItemPageDto();

        Page<ItemDto> itemBoards = itemRepository.findAllItem(pageable, firstCategory, secondCategory);
        int homeStartPage = Math.max(1, itemBoards.getPageable().getPageNumber() - 4);
        int homeEndPage = Math.min(itemBoards.getTotalPages(), itemBoards.getPageable().getPageNumber() + 4);

        itemPageDto.setItemBoards(itemBoards);
        itemPageDto.setHomeStartPage(homeStartPage);
        itemPageDto.setHomeEndPage(homeEndPage);

        return itemPageDto;
    }

    @Override
    public List<ItemDto> getAllItemInBasket(List<Basket> basketList) {
        List<ItemDto> allItemInBasket = new ArrayList<>();

        for(int i= 0; i< basketList.size();i++){
            Long itemId = basketList.get(i).getItem().getId();
            allItemInBasket.add(itemRepository.findAllItemInBasket(itemId));
        }
        return allItemInBasket;
    }

    @Override
    public List<ItemDto> itemToPayment(String itemList) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        ItemDto itemDto = new ItemDto();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(itemList);
        JsonObject object = (JsonObject) jsonArray.get(0);

        String id = object.get("idx").getAsString();
        String color = object.get("color").getAsString();
        String quantity = object.get("quantity").getAsString();

        Long itemIdx = Long.parseLong(id);
        int orderCount = Integer.parseInt(quantity);

        Item byItemIdxAndColorAndRep = itemRepository.findByItemIdxAndColorAndRep(itemIdx, color, true);
        itemDto.setItemIdx(byItemIdxAndColorAndRep.getItemIdx());
        itemDto.setItemName(byItemIdxAndColorAndRep.getItemName());
        itemDto.setColor(byItemIdxAndColorAndRep.getColor());
        itemDto.setBasketCount(orderCount);
        itemDto.setId(byItemIdxAndColorAndRep.getId());
        itemDto.setPrice(byItemIdxAndColorAndRep.getPrice());
        itemDto.setImgUrl(byItemIdxAndColorAndRep.getImgUrl());

        itemDtoList.add(itemDto);

        return itemDtoList;
    }

    @Override
    public ItemListToOrderDto itemToOrder(String orderItemInfo) {
        ItemListToOrderDto itemListToOrderDto = new ItemListToOrderDto();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(orderItemInfo);

        List<Long> itemList = new ArrayList<>();
        List<Integer> itemCountList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = (JsonObject) jsonArray.get(i);
            String item_idx = object.get("item_idx").getAsString();
            String item_color = object.get("item_color").getAsString();
            String item_quantity = object.get("item_quantity").getAsString();

            Long itemIdx = Long.parseLong(item_idx);
            int itemOrderCount = Integer.parseInt(item_quantity);

            Item findItem = itemRepository.findByItemIdxAndColorAndRep(itemIdx, item_color, true);

            itemList.add(findItem.getId());
            itemCountList.add(itemOrderCount);
        }

        itemListToOrderDto.setItemList(itemList);
        itemListToOrderDto.setItemCountList(itemCountList);

        return itemListToOrderDto;
    }

    @Override
    public List<Item> getMainCarouselItemList() {
        List<Item> mainCarouselList = new ArrayList<>();

        Item firstItem = itemRepository.findByItemIdxAndColorAndRep(94L, "블루", true);
        Item secondItem = itemRepository.findByItemIdxAndColorAndRep(95L, "블랙", true);
        Item thirdItem = itemRepository.findByItemIdxAndColorAndRep(96L, "네이비", true);
        Item fourthItem = itemRepository.findByItemIdxAndColorAndRep(97L, "블랙 M size", true);
        Item fifthItem = itemRepository.findByItemIdxAndColorAndRep(98L, "아이보리", true);

        mainCarouselList.add(firstItem);
        mainCarouselList.add(secondItem);
        mainCarouselList.add(thirdItem);
        mainCarouselList.add(fourthItem);
        mainCarouselList.add(fifthItem);

        return mainCarouselList;
    }
//    이 메소드는 임의로 상품을 지정해서 Main Carousel에 담는 역할이다.

    @Override
    public List<WeeklyBestDto> getOuterWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("outer", "jacket", true);
    }

    @Override
    public List<WeeklyBestDto> getSleeveTopWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("top", "longsleeve", true);
    }

    @Override
    public List<WeeklyBestDto> getShirtsWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("shirts", "basic", true);
    }

    @Override
    public List<WeeklyBestDto> getBottomWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("bottom", "cotton", true);
    }

    @Override
    public List<WeeklyBestDto> getShoesWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("shoes", "shoes", true);
    }

    @Override
    public List<WeeklyBestDto> getTopKnitWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("top", "knit", true);
    }

    @Override
    public List<WeeklyBestDto> getNewArrivalItem() {
        List<WeeklyBestDto> newArrivalList = itemRepository.findNewArrivalItem("outer", "jacket", true);
        for (int i = 0; i < newArrivalList.size(); i++) {
            newArrivalList.get(i).setMileage(newArrivalList.get(i).getPrice() / 100);
        }

        return newArrivalList;
    }

    @Override
    public Page<ItemDto> findAllItem(Pageable pageable) {
        return itemRepository.searchAllItem(pageable);
    }

    @Override
    public ItemPageDto findAllItemByPaging(Pageable pageable){
        ItemPageDto itemPageDto = new ItemPageDto();
        Page<ItemDto> itemBoards = itemRepository.searchAllItem(pageable);

        int homeStartPage = Math.max(1, itemBoards.getPageable().getPageNumber() - 1);
        int homeEndPage = Math.min(itemBoards.getTotalPages(), itemBoards.getPageable().getPageNumber() + 3);

        itemPageDto.setItemBoards(itemBoards);
        itemPageDto.setHomeStartPage(homeStartPage);
        itemPageDto.setHomeEndPage(homeEndPage);

        return itemPageDto;
    }

    @Override
    public ItemPageDto findAllItemByConditionByPaging(SearchItem searchItem, Pageable pageable) {
        ItemPageDto itemPageDto = new ItemPageDto();
        Page<ItemDto> itemBoards = itemRepository.searchAllItemByCondition(searchItem, pageable);

        int homeStartPage = Math.max(1, itemBoards.getPageable().getPageNumber() - 1);
        int homeEndPage = Math.min(itemBoards.getTotalPages(), itemBoards.getPageable().getPageNumber() + 3);

        itemPageDto.setItemBoards(itemBoards);
        itemPageDto.setHomeStartPage(homeStartPage);
        itemPageDto.setHomeEndPage(homeEndPage);

        return itemPageDto;
    }

    @Override
    public Page<ItemDto> findAllItemByCondition(SearchItem searchItem, Pageable pageable){
        return itemRepository.searchAllItemByCondition(searchItem,pageable);
    }

    @Override
    public Long getMaxItemIdx() {
        return itemRepository.searchMaxItemIdx();
    }


}
