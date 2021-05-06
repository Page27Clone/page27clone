package com.page27.project.domain;

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
    @GeneratedValue
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
}
