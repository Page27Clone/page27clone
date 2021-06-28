package com.page27.project.dto;


import com.page27.project.domain.MemberGrade;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String name;
    private String loginId;
    private MemberGrade memberGrade;
    private String phoneNumber;
    private int visitCount;
    private int orderCount;
    private LocalDate createdAt;


    @QueryProjection
    public MemberDto(Long id, String name, String loginId, MemberGrade memberGrade, String phoneNumber, int visitCount, int orderCount, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.memberGrade = memberGrade;
        this.phoneNumber = phoneNumber;
        this.visitCount = visitCount;
        this.orderCount = orderCount;
        this.createdAt = createdAt;
    }
}// Querydsl을 위한 Dto
