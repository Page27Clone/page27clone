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

    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }// 모든 상품 조회

    public Item findOneItem(Long id){
        return itemRepository.findById(id).get();
    }
}
