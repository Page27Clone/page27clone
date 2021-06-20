package com.page27.project.controller.main;

import com.page27.project.domain.Mileage;
import com.page27.project.dto.MemberInfoDto;
import com.page27.project.service.MemberService;
import com.page27.project.service.MileageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final MileageService mileageService;

    @GetMapping("main/login")
    public String getLoginPage(){
        return "main/login";
    }

    @GetMapping("main/register")
    public String getRegisterPage(){
        return "main/register_user";
    }

    @PostMapping("main/register")
    public String doRegisterPage(MemberInfoDto memberInfoDto){
        Long memberId = memberService.joinUser(memberInfoDto);
        mileageService.joinUserMileage(memberId);

        return "redirect:/main/login";
    }
}
