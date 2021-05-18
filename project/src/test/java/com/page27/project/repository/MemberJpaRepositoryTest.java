package com.page27.project.repository;

import com.page27.project.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

//    @Test
//    public void basicTest(){
//        Member member = new Member();
//        member.setName("이수형");
//        memberJpaRepository.save(member);
//
//        Member findMember = memberJpaRepository.findById(member.getId()).get();
//
//        Assertions.assertThat(findMember).isEqualTo(member);
//
//        List<Member> result1 = memberJpaRepository.findAll();
//        Assertions.assertThat(result1).containsExactly(member);
//
//        List<Member> result2 = memberJpaRepository.findByUsername("이수형");
//        Assertions.assertThat(result2).containsExactly(member);
//    }

//    @Test
//    public void basicQuerydslTest(){
//        Member member = new Member();
//        member.setName("이수형");
//        memberJpaRepository.save(member);
//
//        Member findMember = memberJpaRepository.findById(member.getId()).get();
//
//        Assertions.assertThat(findMember).isEqualTo(member);
//
//        List<Member> result1 = memberJpaRepository.findAll_Querydsl();
//        Assertions.assertThat(result1).containsExactly(member);
//
//        List<Member> result2 = memberJpaRepository.findByUsername_Querydsl("이수형");
//        Assertions.assertThat(result2).containsExactly(member);
//    }

}