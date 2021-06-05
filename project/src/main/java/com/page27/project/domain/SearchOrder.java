package com.page27.project.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SearchOrder {
    private String firstdate;
    private String lastdate;
    private String omode;
    private String sinput;
}
