package com.page27.project.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    private String loginId;

    private String password;

    private String name;

    private String phoneNumber;

    private String email;

    private String birthday;
}
// 회원가입 폼
