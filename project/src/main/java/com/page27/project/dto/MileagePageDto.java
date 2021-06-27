package com.page27.project.dto;

import com.page27.project.domain.Mileage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class MileagePageDto {
    Page<Mileage> mileageBoards;
    int homeStartPage;
    int homeEndPage;
}
