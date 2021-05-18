package com.page27.project.querydsl;

import com.page27.project.domain.Member;
import com.page27.project.domain.QMember;
import com.page27.project.dto.MemberDto;
import com.page27.project.dto.QMemberDto;
import com.page27.project.dto.UserDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;


    @Test
    public void startQuerydsl(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        Member findMember = queryFactory
                .select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.name.eq("memberA"))
                .fetchOne();

        Assertions.assertThat(findMember.getName()).isEqualTo("memberA");
    }

    @Test
    public void search(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        Member findMember = queryFactory
                .select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.name.eq("memberA").and(QMember.member.password.eq("1234")))
                .fetchOne();

        Assertions.assertThat(findMember.getName()).isEqualTo("memberA");
        Assertions.assertThat(findMember.getPassword()).isEqualTo("1234");
    }

    @Test
    public void searchAndParam(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        Member findMember = queryFactory
                .select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.name.eq("memberA"),
                        (QMember.member.password.eq("1234"))
                )
                .fetchOne();

        Assertions.assertThat(findMember.getName()).isEqualTo("memberA");
        Assertions.assertThat(findMember.getPassword()).isEqualTo("1234");
    }

    @Test
    public void simpleProjection(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//        List<String> result = queryFactory
//                .select(QMember.member.name)
//                .from(QMember.member)
//                .fetch();
//
//        for(String s : result) {
//            System.out.println("result = " + s);
//        }

        List<Member> resultMember = queryFactory
                .select(QMember.member)
                .from(QMember.member)
                .fetch();

        for(Member m : resultMember){
            System.out.println("Member result = " + resultMember);
        }
    }

    @Test
    public void findDtoBySetter(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<MemberDto> result = queryFactory
                .select(Projections.bean(MemberDto.class,
                        QMember.member.name,
                        QMember.member.loginId))
                .from(QMember.member)
                .fetch();

        for(MemberDto memberDto : result){
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    public void findDtoByField(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class,
                        QMember.member.name,
                        QMember.member.loginId))
                .from(QMember.member)
                .fetch();

        for(MemberDto memberDto : result){
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    public void findDtoByConstructor(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<UserDto> result = queryFactory
                .select(Projections.fields(UserDto.class,
                        QMember.member.name.as("username"),
                        QMember.member.loginId))
                .from(QMember.member)
                .fetch();

        for(UserDto userDto : result){
            System.out.println("memberDto = " + userDto);
        }
    }

//    @Test
//    public void findDtoByQueryProjection(){
//        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//
//        List<MemberDto> result = queryFactory
//                .select(new QMemberDto(QMember.member.name, QMember.member.loginId))
//                .from(QMember.member)
//                .fetch();
//
//        for(MemberDto memberDto : result){
//            System.out.println("memberDto = " + memberDto);
//        }
//    }

    @Test
    public void dynamicQuery_BooleanBuilder(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        String usernameParam = "memberA";
        String loginIdParam = "memberA_Id";

        List<Member> result = searchMember1(usernameParam,loginIdParam);
        Assertions.assertThat(result.size()).isEqualTo(1);


    }

    private List<Member> searchMember1(String usernameCond, String loginIdCond) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        BooleanBuilder builder = new BooleanBuilder();
        if(usernameCond != null){
            builder.and(QMember.member.name.eq(usernameCond));
        }
        if(loginIdCond!= null){
            builder.and(QMember.member.loginId.eq(loginIdCond));
        }

        return queryFactory
                .selectFrom(QMember.member)
                .where(builder)
                .fetch();
    }
    
    @Test
    public void dynamicQuery_WhereParam(){
        String usernameParam = "memberA";
        String loginIdParam = "memberA_Id";

        List<Member> result = searchMember2(usernameParam,loginIdParam);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, String loginIdCond) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .selectFrom(QMember.member)
                //.where(usernameEq(usernameCond),loginIdEq(loginIdCond))
                .where(allEq(usernameCond,loginIdCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {
        if(usernameCond != null){
            return QMember.member.name.eq(usernameCond);
        }else{
            return null;
        }
    }

    private BooleanExpression loginIdEq(String loginIdCond) {
        if(loginIdCond == null) {
            return null;
        }
        return QMember.member.loginId.eq(loginIdCond);
    }

    private BooleanExpression allEq(String usernameCond, String loginIdCOnd){
        return usernameEq(usernameCond).and(loginIdEq(loginIdCOnd));
    }


}
