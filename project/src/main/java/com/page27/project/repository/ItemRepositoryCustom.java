package com.page27.project.repository;

import com.page27.project.domain.SearchItem;
import com.page27.project.dto.ItemDto;
import com.page27.project.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<ItemDto> searchAllItem(Pageable pageable);
    Page<ItemDto> searchAllItemByCondition(SearchItem searchItem, Pageable pageable);
}
