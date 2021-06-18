package com.page27.project.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketMemberMileageDto {
    private String loginId;
    private String name;
    private String memberGrade;
    private int totalMileage;
    private int usedMileage;
    private int restMileage;
}
