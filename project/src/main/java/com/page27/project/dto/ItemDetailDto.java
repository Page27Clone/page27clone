package com.page27.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemDetailDto {
    private Long itemIdx;
    private String imgMainUrl;
    private String itemName;
    private int price;
    private double mileage;
    private String itemInfo;
    private List<String> colorList;
    private String fabric;
    private String model;
    private List<Long> itemId;
    private List<String> imgUrlList;
//    private List<String> saleList;
}
