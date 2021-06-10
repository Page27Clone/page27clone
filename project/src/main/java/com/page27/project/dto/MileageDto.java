package com.page27.project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
