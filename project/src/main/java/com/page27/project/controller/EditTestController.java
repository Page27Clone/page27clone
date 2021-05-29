package com.page27.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditTestController {
    @GetMapping("/changeaddress")
    public String changeaddress(Model model) {
        String springVersion = org.springframework.core.SpringVersion.getVersion();
        System.out.println("스프링 프레임워크 버전 : " + springVersion);
        return "/main/change_address";
    }

    @PutMapping("/changeaddress_ok")
    public String changeaddress_ok(@RequestParam String address_name, Model model) {
        System.out.println(address_name);
        return "/main/change_address";
    }
}
