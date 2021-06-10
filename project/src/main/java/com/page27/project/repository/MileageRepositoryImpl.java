package com.page27.project.repository;

import com.page27.project.domain.QMember;
import com.page27.project.domain.QMileage;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class MileageRepositoryImpl implements MileageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public MileageRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public int searchAllMileage() {
//        List<Tuple> result = queryFactory
//                .select(QMember.member.name, QMileage.mileage.mileagePrice.sum())
//                .from(QMileage.mileage)
//                .join(QMileage.mileage.member,QMember.member)
//                .groupBy(QMember.member.loginId)
//                .fetch();

        return 0;
    }
}
