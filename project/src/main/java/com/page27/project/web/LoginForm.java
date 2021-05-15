package com.page27.project.web;

import lombok.Getter;

import java.util.List;

@Getter
public class LoginForm {
    private String loginId;

    private String password;

    private List<String> imgUrl;
}
