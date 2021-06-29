package com.page27.project.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchOrder {
    private String firstdate;
    private String lastdate;
    private String omode;
    private String sinput;
}
