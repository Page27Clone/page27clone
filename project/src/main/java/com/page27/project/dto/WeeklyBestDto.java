package com.page27.project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeeklyBestDto {
    private Long itemIdx;
    private String itemName;
    private int price;
    private String itemInfo;
    private String imgUrl;

    @QueryProjection
    public WeeklyBestDto(Long itemIdx, String itemName, int price, String itemInfo, String imgUrl) {
        this.itemIdx = itemIdx;
        this.itemName = itemName;
        this.price = price;
        this.itemInfo = itemInfo;
        this.imgUrl = imgUrl;
    }
}
