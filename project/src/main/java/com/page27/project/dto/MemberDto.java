package com.page27.project.dto;

import com.page27.project.domain.Member;
import com.page27.project.domain.MemberGrade;
import com.page27.project.domain.Mileage;
import lombok.Getter;

@Getter
public class MemberDto {
    private Long id;

    private String name;

    private String loginId;

    private String password;

    private String phoneNumber;

    private String email;

    private String birthday;

    private MemberGrade memberGrade;

    private int visitCount;

    private int orderCount;

    private Mileage mileage;

    public MemberDto(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.phoneNumber = member.getPhoneNumber();
        this.email = member.getEmail();
        this.memberGrade = member.getMemberGrade();
        this.birthday = member.getBirthday();
        this.visitCount = member.getVisitCount();
        this.orderCount = member.getOrderCount();
        //this.mileage = member.getMileage();
    }
}
