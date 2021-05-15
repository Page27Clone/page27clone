package com.page27.project.service;

import com.page27.project.domain.Member;
import com.page27.project.exception.DuplicateException;
import com.page27.project.repository.MemberRepository;

import org.junit.jupiter.api.Assertions;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;




@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("test1");

        Long saveCheckId = memberService.join(member);

        Assertions.assertEquals(member,memberService.findOneMember(saveCheckId));
    }

    @Test//(expected = DuplicateException.class)
    public void 중복회원가입_방지() throws Exception{
        //Given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //When
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 한다.
        //Then

    }

    @Test
    public void 멤버_찾기() throws Exception{
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        memberRepository.save(member1);

        member2 = memberRepository.findById(1L).get();

        Assertions.assertEquals(member1,member2);

    }
}