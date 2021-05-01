package com.page27.project.controller;

import com.page27.project.domain.Member;
import com.page27.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

//    @GetMapping("why")
//    public String getMember(){
//        return "memberList";
//    }
//
//    @GetMapping("home")
//    public String getHome(){
//        return "home";
//    }
//
//    @GetMapping("hello")
//    public String hello(Model model) {
//        model.addAttribute("data", "hello!!");
//        return "hello";
//    }

    @GetMapping("adminUserlist")
    public String adminUserlist(Model model, Pageable pageable) {
        Page<Member> page = memberService.findMembers();
        model.addAttribute("userList",page.getContent());
        return "admin_userlist";
    }

}
