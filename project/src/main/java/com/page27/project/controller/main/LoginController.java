package com.page27.project.controller.main;

import com.page27.project.domain.Mileage;
import com.page27.project.dto.MemberInfoDto;
import com.page27.project.service.MemberService;
import com.page27.project.service.MileageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final MileageService mileageService;

    @GetMapping("main/login")
    public String getLoginPage(HttpServletRequest request, @RequestParam(value = "error",required = false) String error, Model model){
        String referer = request.getHeader("Referer");

        if(referer != null){
            request.getSession().setAttribute("prevPage",referer);
        }
        else {
            referer = "http://localhost:8080/main/index";
            request.getSession().setAttribute("prevPage",referer);
        }
        model.addAttribute("error",error);
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

    @GetMapping("/defaultUrl")
    public String loginRedirectPage(HttpServletRequest request){
        String referer = request.getHeader("Referer");

        referer = "http://localhost:8080/main/index";
        request.getSession().setAttribute("prevPage",referer);

        return "redirect:/main/index";
    }
//    nav bar 로그인 구현
}
