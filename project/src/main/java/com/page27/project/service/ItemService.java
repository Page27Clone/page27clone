package com.page27.project.service;

import com.page27.project.domain.Item;
import com.page27.project.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;


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
}
