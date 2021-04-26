package com.page27.project.repository;

import com.page27.project.domain.Member;
import com.page27.project.domain.MemberGrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember(){
        Member member1 = new Member();
        member1.setEmail("skyey94@naver.com");
        member1.setMemberGrade(MemberGrade.USER);
        member1.setName("memberA");
        member1.setLoginId("memberAId");
        member1.setPassword("1234");
        member1.setPhoneNumber("010-1234-1234");
        memberRepository.save(member1);

        //데이터 넣기
        addMember("skyey94@naver.com","memberB","memberB_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberC","memberC_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberD","memberD_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberE","memberE_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberF","memberF_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberG","memberG_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberH","memberH_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberI","memberI_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberJ","memberJ_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberK","memberK_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberL","memberL_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberM","memberM_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberN","memberN_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberO","memberO_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberP","memberP_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberQ","memberQ_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberR","memberR_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberS","memberS_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberT","memberT_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberU","memberU_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberV","memberV_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberW","memberW_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberX","memberX_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberY","memberY_Id","1234","010-1234-1234");
        addMember("skyey94@naver.com","memberZ","memberZ_Id","1234","010-1234-1234");
    }

    public void addMember(String email, String name,String loginId,String password, String phonenumber){
        Member member = new Member();
        member.setEmail(email);
        member.setMemberGrade(MemberGrade.USER);
        member.setName(name);
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setPhoneNumber(phonenumber);

        memberRepository.save(member);
    }




}