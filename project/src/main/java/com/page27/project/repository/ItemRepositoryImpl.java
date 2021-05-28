package com.page27.project.repository;

import com.page27.project.dto.ItemDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<ItemDto> searchAll(Pageable pageable) {
        return null;
    }
}
