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
    private int price;
    private String saleStatus;
    private String imgUrl;
    private String color;



    @QueryProjection
    public ItemDto(Long id, String itemName, String firstCategory, int price, String saleStatus,String imgUrl,String color) {
//        System.out.println(imgUrl.split(",")[0]);
//        String[] imgArr = imgUrl.split(",");
        this.id = id;
        this.itemName = itemName;
        this.firstCategory = firstCategory;
        this.price = price;
        this.saleStatus = saleStatus;
        this.imgUrl = imgUrl;
        this.color = color;

//        System.out.println("here test " + imgArr[0]);
//        System.out.println("here test 7 " + imgArr.toString());
    }
}


