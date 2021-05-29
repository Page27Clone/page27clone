package com.page27.project.service;

import com.page27.project.domain.Item;
import com.page27.project.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;


    @Transactional
    public Long save(Item item){
        itemRepository.save(item);

        return item.getId();
    }// 상품 저장
}
