package com.page27.project.repository;

import com.page27.project.domain.Address;
import com.page27.project.domain.Member;
import com.page27.project.domain.Mileage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MileageRepositoryTest {

    @Autowired
    MileageRepository mileageRepository;

    @Autowired
    MemberRepository memberRepository;


    private Member createTestMember(){
        Member member = new Member();
        member.setName("testMember1");
        member.setAddress(new Address("Seoul","Gangnam","13600"));
        member.setVisitCount(5);


        Mileage mileage1 = new Mileage();
        mileage1.setMileageContent("회원가입 적립금");
        mileage1.setMileagePrice(1000);
        mileage1.setMember(member);

        Mileage mileage2 = new Mileage();
        mileage2.setMileageContent("1주년 기념 적립금");
        mileage2.setMileagePrice(2000);
        mileage2.setMember(member);

        memberRepository.save(member);

        return member;
    }



}