//package com.page27.project.repository;
//
//import com.page27.project.domain.QMember;
//import com.page27.project.domain.QOrder;
//import com.page27.project.domain.QOrderItem;
//import com.page27.project.dto.MemberDto;
//import com.page27.project.dto.OrderDto;
//import com.page27.project.dto.QMemberDto;
//import com.page27.project.dto.QOrderDto;
//import com.querydsl.core.QueryResults;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@RequiredArgsConstructor
//public class OrderRepositoryImpl implements OrderRepositoryCustom {
//
//    private final JPAQueryFactory queryFactory;
//
//    public OrderRepositoryImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//    @Override
//    public Page<OrderDto> searchAllItem(Pageable pageable) {
//
//        QueryResults<OrderDto> results = queryFactory
//                .select(new QOrderDto(
//                        QMember.member.name,
//                        QOrder.order.orderedAt,
//                        QOrder.order.payment,
//                        QOrder.order.orderItemList,
//                        QOrder.order.totalPrice
//                ))
//                .from(QOrder.order)
//                .leftJoin(QOrder.order.member,QMember.member)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//
//        List<OrderDto> content = results.getResults();
//        long total = results.getTotal();
//
//        return new PageImpl<>(content, pageable, total);
//    }
//}
