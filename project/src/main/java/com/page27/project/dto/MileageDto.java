package com.page27.project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MileageDto {
    private Long id;
    private int mileagePrice;
    private String mileageContent;

    @QueryProjection
    public MileageDto(Long id, int mileagePrice, String mileageContent) {
        this.id = id;
        this.mileagePrice = mileagePrice;
        this.mileageContent = mileageContent;
    }
}
