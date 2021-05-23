package com.page27.project.repository;



import com.page27.project.domain.QMember;
import com.page27.project.domain.Search;
import com.page27.project.dto.MemberDto;

import com.page27.project.dto.QMemberDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberDto> searchAll(Pageable pageable) {
        QueryResults<MemberDto> results = queryFactory
                .select(new QMemberDto(
                        QMember.member.id,
                        QMember.member.name,
                        QMember.member.loginId,
                        QMember.member.memberGrade,
                        QMember.member.phoneNumber,
                        QMember.member.visitCount,
                        QMember.member.orderCount,
                        QMember.member.createdAt
                ))
                .from(QMember.member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MemberDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }


    @Override
    public Page<MemberDto> searchByCondition(Search search, Pageable pageable) {
        QueryResults<MemberDto> results = queryFactory
                .select(new QMemberDto(
                        QMember.member.id,
                        QMember.member.name,
                        QMember.member.loginId,
                        QMember.member.memberGrade,
                        QMember.member.phoneNumber,
                        QMember.member.visitCount,
                        QMember.member.orderCount,
                        QMember.member.createdAt
                ))
                .from(QMember.member)
                .where(nameEq(search.getSearchKeyword()).or(loginIdEq(search.getSearchKeyword())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MemberDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression loginIdEq(String loginIdCondition) {
        if (loginIdCondition == null) {
            return null;
        }
        return QMember.member.loginId.like("%" + loginIdCondition + "%");
    }

    private BooleanExpression nameEq(String nameCondition) {
        if (nameCondition == null) {
            return null;
        }
        return QMember.member.name.like("%" + nameCondition + "%");
    }
}
