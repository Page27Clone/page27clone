package com.page27.project.repository;

import com.page27.project.domain.Member;
import com.page27.project.domain.MemberAddress;
import com.page27.project.domain.Mileage;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class MileageRepositoryTest {

    @Autowired
    MileageRepository mileageRepository;

    @Autowired
    MemberRepository memberRepository;


    private Member createTestMember() {
        Member member = new Member();
        member.setName("testMember1");
        member.setMemberAddress(new MemberAddress("Seoul", "Gangnam", "13600"));
        member.setVisitCount(5);


        Mileage mileage1 = new Mileage();
        mileage1.setMileageContent("회원가입 적립금");
        mileage1.setMileagePrice(1000);
        mileage1.setMember(member);

        Mileage mileage2 = new Mileage();
        mileage2.setMileageContent("1주년 기념 적립금");
        mileage2.setMileagePrice(2000);
        mileage2.setMember(member);

        Mileage byId = mileageRepository.findById(mileage1.getId()).get();
        Mileage byId2 = mileageRepository.findById(mileage2.getId()).get();

        memberRepository.save(member);

        Assertions.assertThat(byId).isEqualTo(mileage1);
        Assertions.assertThat(byId2).isEqualTo(mileage2);

        return member;
    }


}