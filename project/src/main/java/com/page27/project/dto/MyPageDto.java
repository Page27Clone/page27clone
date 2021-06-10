package com.page27.project.dto;

import com.page27.project.domain.MemberGrade;
import lombok.Data;

@Data
public class MyPageDto {
    private String name;
    private MemberGrade grade;
    private int mileage;
}
