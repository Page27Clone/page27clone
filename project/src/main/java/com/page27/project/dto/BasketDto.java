package com.page27.project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketDto {
    private Long basketId;
    private int basketCount;
    private Long itemId;
    private String itemName;
    private String color;
    private String imgUrl;
    private int price;


    @QueryProjection
    public BasketDto(Long basketId, int basketCount, Long itemId, String itemName, String color, String imgUrl, int price) {
        this.basketId = basketId;
        this.basketCount = basketCount;
        this.itemId = itemId;
        this.itemName = itemName;
        this.color = color;
        this.imgUrl = imgUrl;
        this.price = price;
    }


}
