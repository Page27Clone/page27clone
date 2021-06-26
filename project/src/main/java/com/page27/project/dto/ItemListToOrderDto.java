package com.page27.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemListToOrderDto {
    private List<Long> itemList;
    private List<Integer> itemCountList;
}
