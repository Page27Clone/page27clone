package com.page27.project.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SearchMember {
    //타임리프의 name이 이 부분과 같아야 한다.
    private String searchCondition;
    private String searchKeyword;
}
