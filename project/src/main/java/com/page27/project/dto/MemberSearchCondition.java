package com.page27.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchCondition {
    private String name;
    private String loginId;

    public MemberSearchCondition(String name, String loginId) {
        this.name = name;
        this.loginId = loginId;
    }
}
