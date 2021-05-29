package com.page27.project.repository;

import com.page27.project.domain.QItem;
import com.page27.project.domain.QMember;
import com.page27.project.dto.ItemDto;
import com.page27.project.dto.MemberDto;
import com.page27.project.dto.QItemDto;
import com.page27.project.dto.QMemberDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;


public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ItemDto> searchAll(Pageable pageable) {

//        System.out.println("here4" + QItem.item.imgUrl);
//        System.out.println("here5" + QItem.item.imgUrl.substring(0));

        QueryResults<ItemDto> results = queryFactory
                .select(new QItemDto(
                        QItem.item.id,
                        QItem.item.itemName,
                        QItem.item.firstCategory,
                        QItem.item.price,
                        QItem.item.saleStatus,
                        QItem.item.imgUrl,
                        QItem.item.color
                ))
                .from(QItem.item)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ItemDto> content = results.getResults();
//        System.out.println(content);
        long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
}
