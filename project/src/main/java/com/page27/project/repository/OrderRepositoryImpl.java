package com.page27.project.repository;

import com.page27.project.domain.*;
import com.page27.project.dto.OrderDto;
import com.page27.project.dto.QOrderDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import javax.persistence.EntityManager;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderDto> searchAllItem(Pageable pageable) {
        QueryResults<OrderDto> results = queryFactory
                .select(new QOrderDto(
                        QOrder.order.id,
                        QOrderItem.orderItem.id,
                        QMember.member.name,
                        QOrderItem.orderItem.item.itemName,
                        QOrder.order.orderedAt,
                        QOrder.order.payment,
                        QOrderItem.orderItem.orderPrice,
                        QOrderItem.orderItem.orderStatus
                ))
                .from(QOrder.order)
                .leftJoin(QOrderItem.orderItem).on(QOrderItem.orderItem.eq(QOrder.order.orderItemList.any()))
                .leftJoin(QMember.member).on(QMember.member.eq(QOrder.order.member))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<OrderDto> searchByCondition(SearchOrder searchOrder, Pageable pageable) {
        QueryResults<OrderDto> results = queryFactory
                .select(new QOrderDto(
                        QOrder.order.id,
                        QOrderItem.orderItem.id,
                        QMember.member.name,
                        QOrderItem.orderItem.item.itemName,
                        QOrder.order.orderedAt,
                        QOrder.order.payment,
                        QOrderItem.orderItem.orderPrice,
                        QOrderItem.orderItem.orderStatus
                ))
                .from(QOrder.order)
                .leftJoin(QOrderItem.orderItem).on(QOrderItem.orderItem.eq(QOrder.order.orderItemList.any()))
                .leftJoin(QMember.member).on(QMember.member.eq(QOrder.order.member))
                .where(buyerEq(searchOrder.getSinput()),
                        orderStatusEq(searchOrder.getOmode()),
                        betweenDate(searchOrder.getFirstdate(), searchOrder.getLastdate())
                )
//                이게 검색어가 입력이 되어야지 메소드가 실현됨 그래서 getOmode만으로는 메소드가 실행이 안된다.
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression betweenDate(String firstDate, String lastDate){
        if(StringUtils.isEmpty(firstDate) && StringUtils.isEmpty(lastDate)){

            LocalDate start = LocalDate.now().minusYears(10L);
            LocalDate end = LocalDate.now();

            logger.info(firstDate);
            logger.info(lastDate);

            logger.info("here none both parmeter");
            logger.info("start = " + start);
            logger.info("end = " + end);

            return QOrder.order.orderedAt.between(start,end);
        }
        else if(StringUtils.isNotEmpty(firstDate) && StringUtils.isEmpty(lastDate)){

            logger.info("here none lastDate parmeter");

            logger.info(firstDate);
            logger.info(lastDate);

            LocalDate start = LocalDate.parse(firstDate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate end = LocalDate.now();

            logger.info("start = " + start);
            logger.info("end = " + end);

            return QOrder.order.orderedAt.between(start,end);
        }
        else if(StringUtils.isEmpty(firstDate) && StringUtils.isNotEmpty(lastDate)){

            logger.info("here none firstDate parmeter");

            logger.info(firstDate);
            logger.info(lastDate);

            LocalDate start = LocalDate.now().minusYears(10L);
//           LocalDate 와 LocalDateTime의 차이로 인하여 min을 하면 형식이 이것이 아니라 변하게 된다. 그래서 임의로 10년 전으로 계산한다.
            LocalDate end = LocalDate.parse(lastDate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            logger.info("start = " + start);
            logger.info("end = " + end);

            return QOrder.order.orderedAt.between(start,end);
        }
        else{
            logger.info("here both parmeter");

            logger.info(firstDate);
            logger.info(lastDate);

            LocalDate start = LocalDate.parse(firstDate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            logger.info("start ok");

            LocalDate end = LocalDate.parse(lastDate);

            logger.info("start = " + start);
            logger.info("end = " + end);

            return QOrder.order.orderedAt.between(start,end);
        }
    }//날짜 비교 지금 안됨

    private BooleanExpression buyerEq(String buyerCondition){
        if(StringUtils.isEmpty(buyerCondition)){
            return null;
        }
        return QMember.member.name.likeIgnoreCase("%" + buyerCondition + "%");
    }

    private BooleanExpression orderStatusEq(String orderStatusCondition){
        if(StringUtils.isEmpty(orderStatusCondition)){
            return null;
        }
        return QOrderItem.orderItem.orderStatus.eq(OrderStatus.valueOf(orderStatusCondition));
    }
}
//SELECT * FROM ORDERS LEFT JOIN MEMBER ON ORDERS.MEMBER_ID = MEMBER.MEMBER_ID JOIN ORDER_ITEM ON ORDERS.ORDER_ID = ORDER_ITEM.ORDER_ID
