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

    private String headerCategory;

    private String subCategory;

    private String itemName;

    private int price;

    private String itemInfo;

    private String color;

    private String fabric;

    private String model;

    private String size;

    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItemList = new ArrayList<>();

    /*비즈니스 로직*/
    public void plusStockQuantity(int plusQuantity){
        this.stockQuantity += plusQuantity;
    }// 재고가 증가하는 메소드

    public void minusStockQuantity(int minusQuantity){
        int resultStock = this.stockQuantity - minusQuantity;
        if(resultStock < 0){
            throw new NotEnoughStockException("재고가 부족합니다.");
        }else{
            this.stockQuantity = +resultStock;
        }
    }// 재고가 감소하는 메소드

}