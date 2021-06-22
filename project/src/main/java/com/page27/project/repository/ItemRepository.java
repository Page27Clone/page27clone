package com.page27.project.repository;

import com.page27.project.domain.Item;
import com.page27.project.dto.ItemDto;
import com.page27.project.dto.MemberDto;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>,ItemRepositoryCustom{
    List<Item> findAllByItemIdxAndColor(Long itemIdx, String color);
    List<Item> findAllByItemIdx(Long itemIdx);
    List<Item> findAllByItemIdxAndRep(Long itemIdx,boolean rep);
    Item findTopByItemIdxAndRep(Long itemIdx,boolean rep);
    Item findByItemIdxAndColorAndRep(Long itemIdx,String color,boolean rep);
    List<Item> findDistinctTop9ByFirstCategoryAndRep(String firstCategory, boolean rep);
}
