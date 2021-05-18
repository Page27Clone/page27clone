package com.page27.project.repository;

import com.page27.project.domain.Member;
import com.page27.project.dto.MemberDto;
import com.page27.project.dto.MemberSearchCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    private MemberRepository memberRepository;
    @Test
    public void springDataJpaTest(){
        Member member = new Member();
        member.setName("이수형");

        memberRepository.save(member);

        Member findMember = memberRepository.findByName("이수형").get(0);
        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    public void Member_QuerydslTest(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        String nameCondition = "memberA";
        String loginIdCondition = "memberA_Id";
        MemberSearchCondition memberSearchCondition = new MemberSearchCondition(nameCondition,loginIdCondition);
//        List<MemberDto> result = memberRepository.searchName(memberSearchCondition);

//        for(MemberDto memberDto : result){
//            System.out.println("result = " + memberDto);
        }
    }

