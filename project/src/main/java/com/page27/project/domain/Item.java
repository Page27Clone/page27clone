package com.page27.project.domain;

import com.page27.project.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;

    private String itemName;

    private int price;

    private String itemInfo;

    private String color;

    private String fabric;

    private String model;

    private String size;

    private int stockQuantity;

    private String imgUrl;

    private String saleStatus;

    private Long itemIdx;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItemList = new ArrayList<>();

    /*비즈니스 로직*/
    public void plusStockQuantity(int plusQuantity){
        this.stockQuantity += plusQuantity;
    }// 재고가 증가하는 메소드

    public Item(){

    }

    public Item(String firstCategory, String secondCategory, String thirdCategory, String itemName, int price, String itemInfo, String color, String fabric, String model, String size, int stockQuantity, String imgUrl, String saleStatus) {
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.thirdCategory = thirdCategory;
        this.itemName = itemName;
        this.price = price;
        this.itemInfo = itemInfo;
        this.color = color;
        this.fabric = fabric;
        this.model = model;
        this.size = size;
        this.stockQuantity = stockQuantity;
        this.imgUrl = imgUrl;
        this.saleStatus = saleStatus;
    }

    public void minusStockQuantity(int minusQuantity){
        int resultStock = this.stockQuantity - minusQuantity;
        if(resultStock < 0){
            throw new NotEnoughStockException("재고가 부족합니다.");
        }else{
            this.stockQuantity = +resultStock;
        }
    }// 재고가 감소하는 메소드

}