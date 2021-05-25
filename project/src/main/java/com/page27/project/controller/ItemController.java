package com.page27.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    @GetMapping("/admin/register")
    public String registerItemPage(){

        return "admin/admin_registerGoods";
    }
}
