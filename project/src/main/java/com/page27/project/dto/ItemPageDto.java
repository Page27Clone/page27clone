package com.page27.project.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class ItemPageDto {
    Page<ItemDto> itemBoards;
    int homeStartPage;
    int homeEndPage;
}
