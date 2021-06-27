package com.page27.project.repository;

import com.page27.project.domain.SearchItem;
import com.page27.project.dto.ItemDto;
import com.page27.project.dto.MemberDto;
import com.page27.project.dto.WeeklyBestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Page<ItemDto> searchAllItem(Pageable pageable);

    Page<ItemDto> searchAllItemByCondition(SearchItem searchItem, Pageable pageable);

    Page<ItemDto> findAllItem(Pageable pageable,String firstCategory, String secondCategory);

    Long searchMaxItemIdx();

    ItemDto findAllItemInBasket(Long itemId);

    List<WeeklyBestDto> findWeeklyBestItem(String firstCategory,String secondCategory, boolean rep);

    List<WeeklyBestDto> findNewArrivalItem(String firstCategory, String secondCategory, boolean rep);

}
