package com.page27.project.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("main/login")
    public String getLoginPage(){
        return "main/login";
    }

    @GetMapping("main/register")
    public String test(){
        return "main/register_user";
    }
}
