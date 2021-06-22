package com.page27.project.service;

import com.page27.project.domain.Basket;
import com.page27.project.domain.Item;
import com.page27.project.domain.Member;
import com.page27.project.dto.ItemDetailDto;
import com.page27.project.dto.WeeklyBestDto;
import com.page27.project.repository.BasketRepository;
import com.page27.project.repository.ItemRepository;
import com.page27.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;

    @Transactional
    public Long save(Item item){
        itemRepository.save(item);

        return item.getId();
    }// 상품 저장

    @Transactional
    public Long changeItemStatusSoldOut(String idx, String color){
        Long itemIdx = Long.parseLong(idx);
        List<Item> findItem = itemRepository.findAllByItemIdxAndColor(itemIdx,color);
        for(Item changeItem : findItem){
            changeItem.setSaleStatus("soldout");
        }
        return itemIdx;
    }

    @Transactional
    public Long changeItemStatusOnSale(String idx, String color){
        Long itemIdx = Long.parseLong(idx);
        List<Item> findItem = itemRepository.findAllByItemIdxAndColor(itemIdx,color);
        for(Item changeItem : findItem){
            changeItem.setSaleStatus("onsale");
        }
        return itemIdx;
    }

    @Transactional
    public Long deleteItemById(String idx,String color){
        Long itemIdx = Long.parseLong(idx);
        List<Item> findItem = itemRepository.findAllByItemIdxAndColor(itemIdx,color);
        for(Item changeItem : findItem){
            itemRepository.deleteById(changeItem.getId());
        }
        return itemIdx;
    }



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


    public void moveItemToBasket(String loginId,Long itemIdx,String itemColor,int quantity){

        Basket basket = new Basket();
        Member findMember = memberRepository.findByloginId(loginId).get();
        Item findItem = itemRepository.findByItemIdxAndColorAndRep(itemIdx, itemColor, true);

        basket.setMember(findMember);
        basket.setBasketCount(quantity);
        basket.setItem(findItem);

        basketRepository.save(basket);
    }

    public void getWeeklyBestItem(){




    }
}
