package com.page27.project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private String itemName;
    private String firstCategory;
    private String secondCategory;
    private int price;
    private String saleStatus;
    private String imgUrl;
    private String color;
    private boolean rep;
    private Long itemIdx;


    @QueryProjection
    public ItemDto(Long id, String itemName, String firstCategory, int price, String saleStatus, String imgUrl, String color, boolean rep, Long itemIdx) {
        this.id = id;
        this.itemName = itemName;
        this.firstCategory = firstCategory;
        this.price = price;
        this.saleStatus = saleStatus;
        this.imgUrl = imgUrl;
        this.color = color;
        this.rep = rep;
        this.itemIdx = itemIdx;
    }

    @QueryProjection
    public ItemDto(Long id, String itemName, String firstCategory, String secondCategory, int price, String saleStatus, String imgUrl, boolean rep, Long itemIdx ){
        this.id = id;
        this.itemName = itemName;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.price = price;
        this.saleStatus = saleStatus;
        this.imgUrl = imgUrl;
        this.itemIdx = itemIdx;
        this.rep = rep;
    }
}


