package com.page27.project.dto;

import com.page27.project.domain.MemberGrade;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageDto {
    private String name;
    private MemberGrade grade;
    private int mileage;
}
