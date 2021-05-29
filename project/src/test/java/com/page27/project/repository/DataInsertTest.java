package com.page27.project.repository;

import com.page27.project.domain.Address;
import com.page27.project.domain.Member;
import com.page27.project.domain.MemberGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class DataInsertTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember(){
        //데이터 넣기
        addMember("MemberA_Id","1234","MemberA","남자","MemberA@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberB_Id","1234","MemberB","남자","MemberB@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberC_Id","1234","MemberC","남자","MemberC@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberD_Id","1234","MemberD","남자","MemberD@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberE_Id","1234","MemberE","남자","MemberE@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberF_Id","1234","MemberF","남자","MemberF@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberG_Id","1234","MemberG","남자","MemberG@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberH_Id","1234","MemberH","남자","MemberH@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberI_Id","1234","MemberI","남자","MemberI@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberJ_Id","1234","MemberJ","남자","MemberJ@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberK_Id","1234","MemberK","남자","MemberK@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberL_Id","1234","MemberL","남자","MemberL@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberM_Id","1234","MemberM","남자","MemberM@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberN_Id","1234","MemberN","남자","MemberN@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberO_Id","1234","MemberO","남자","MemberO@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberP_Id","1234","MemberP","남자","MemberP@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberQ_Id","1234","MemberQ","남자","MemberQ@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberR_Id","1234","MemberR","남자","MemberR@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberS_Id","1234","MemberS","남자","MemberS@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberT_Id","1234","MemberT","남자","MemberT@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberU_Id","1234","MemberU","남자","MemberU@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberV_Id","1234","MemberV","남자","MemberV@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberW_Id","1234","MemberW","남자","MemberW@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberX_Id","1234","MemberX","남자","MemberX@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberY_Id","1234","MemberY","남자","MemberY@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
        addMember("MemberZ_Id","1234","MemberZ","남자","MemberZ@naver.com","1994-08-09",5,10,"010-1234-1234","031-555-7777");
    }

    public void addMember(String loginId, String password, String name, String sex, String email, String birthday, int visitCount, int orderCount,
                          String phoneNumber, String homePhoneNumber){
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setName(name);
        member.setSex(sex);
        member.setEmail(email);
        member.setBirthday(birthday);
        member.setMemberGrade(MemberGrade.ROLE_USER);
        member.setVisitCount(visitCount);
        member.setOrderCount(orderCount);
        member.setPhoneNumber(phoneNumber);
        member.setHomePhoneNumber(homePhoneNumber);
        Address address = new Address("성남시","수내로","13600");
        member.setAddress(address);

        memberRepository.save(member);
    }
    // 여기까지 Member data 삽입




}