package com.page27.project.controller.main;

import com.page27.project.domain.Member;
import com.page27.project.dto.MainPageOrderDto;
import com.page27.project.dto.MyPageDto;
import com.page27.project.dto.MyPageOrderStatusDto;
import com.page27.project.dto.OrderDto;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.MileageRepository;
import com.page27.project.repository.OrderRepository;
import com.page27.project.service.MileageService;
import com.page27.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final OrderRepository orderRepository;

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

    @GetMapping("/main/order")
    public String getOrderPage(Principal principal, @PageableDefault(size =5) Pageable pageable, Model model){
        String loginId = principal.getName();

        Page<MainPageOrderDto> mainPageOrderBoards = orderRepository.mainPageSearchAllOrder(pageable,loginId);
        int homeStartPage = Math.max(1,mainPageOrderBoards.getPageable().getPageNumber() - 4);
        int homeEndPage = Math.min(mainPageOrderBoards.getTotalPages(),mainPageOrderBoards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",homeStartPage);
        model.addAttribute("endPage",homeEndPage);
        model.addAttribute("orderList",mainPageOrderBoards);

        return "main/order";
    }

}
