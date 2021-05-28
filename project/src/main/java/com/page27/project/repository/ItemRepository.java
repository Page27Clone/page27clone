package com.page27.project.repository;

import com.page27.project.domain.Item;
import com.page27.project.dto.ItemDto;
import com.page27.project.dto.MemberDto;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ItemRepository extends JpaRepository<Item, Long>,ItemRepositoryCustom{
    Page<ItemDto> searchAll(Pageable pageable);
}
