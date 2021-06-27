package com.page27.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ItemToBasketDto {
    private Long id;
    private String item_color;
    private String item_idx;
    private String where;
    private String quantity;
}
