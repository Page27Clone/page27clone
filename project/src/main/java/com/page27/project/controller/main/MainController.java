package com.page27.project.controller.main;

import com.page27.project.domain.Member;
import com.page27.project.dto.MyPageDto;
import com.page27.project.dto.MyPageOrderStatusDto;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.MileageRepository;
import com.page27.project.service.MileageService;
import com.page27.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MemberRepository memberRepository;

    //이건 테스트
    private final MileageRepository mileageRepository;
    private final MileageService mileageService;
    private final OrderService orderService;

    @GetMapping("main")
    public String getMainPage(){
        return "main/index";
    }

    @GetMapping("main/myPage")
    public String getMyPage(Principal principal , Model model){
        MyPageDto myPageDto = new MyPageDto();
        String loginId = principal.getName();
        Member member = memberRepository.findByloginId(loginId).get();
        myPageDto.setName(member.getName());
        myPageDto.setGrade(member.getMemberGrade());
        myPageDto.setMileage(mileageService.getTotalMileage(loginId));

        MyPageOrderStatusDto myPageOrderStatusDto = orderService.showOrderStatus(loginId);


        model.addAttribute("member",myPageDto);
        model.addAttribute("orderStatus",myPageOrderStatusDto);


        return "main/mypage";
    }


    @GetMapping("main/test")
    public String getTestPage(Principal principal){
        System.out.println(principal.getName());

        System.out.println(mileageService.getTotalMileage(principal.getName()));
        System.out.println();
        System.out.println("여기는 메인 컨트롤러 부분" + orderService.showOrderStatus(principal.getName()).getPayWaitingNum());
        System.out.println();

        return "main/logintest";
    }

}
