package com.page27.project.controller.main;

import com.page27.project.domain.*;
import com.page27.project.dto.*;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.MileageRepository;
import com.page27.project.repository.OrderItemRepository;
import com.page27.project.repository.OrderRepository;
import com.page27.project.service.MemberService;
import com.page27.project.service.MileageService;
import com.page27.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //이건 테스트
    private final MileageRepository mileageRepository;
    private final MileageService mileageService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


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
    public String getOrderPage(Principal principal, @PageableDefault(size =2) Pageable pageable, Model model, SearchOrder searchOrder){
        String loginId = principal.getName();
        if(StringUtils.isEmpty(searchOrder.getFirstdate()) && StringUtils.isEmpty(searchOrder.getLastdate())) {
            Page<MainPageOrderDto> mainPageOrderBoards = orderRepository.mainPageSearchAllOrder(pageable, loginId);
            int homeStartPage = Math.max(1, mainPageOrderBoards.getPageable().getPageNumber() - 4);
            int homeEndPage = Math.min(mainPageOrderBoards.getTotalPages(), mainPageOrderBoards.getPageable().getPageNumber() + 4);
            model.addAttribute("startPage", homeStartPage);
            model.addAttribute("endPage", homeEndPage);
            model.addAttribute("orderList", mainPageOrderBoards);

            return "main/order";
        }
        Page<MainPageOrderDto> mainPageOrderBoards = orderRepository.mainPageSearchAllOrderByCondition(searchOrder,pageable,loginId);

        int homeStartPage = Math.max(1, mainPageOrderBoards.getPageable().getPageNumber() - 4);
        int homeEndPage = Math.min(mainPageOrderBoards.getTotalPages(), mainPageOrderBoards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", homeStartPage);
        model.addAttribute("endPage", homeEndPage);
        model.addAttribute("orderList", mainPageOrderBoards);
        return "main/order";
    }

    @ResponseBody
    @PatchMapping("/main/orderStatusChange/{id}")
    public String mainOrderStatusChangePage(@PathVariable Long id, @RequestParam OrderStatus status){
        System.out.println("여기를 못들어옴");
        OrderItem orderItem = orderItemRepository.findById(id).get();
        orderItem.setOrderStatus(status);

        orderItemRepository.save(orderItem);
        return "주문상품 상태 변경완료";
    }

    @GetMapping("/main/profile")
    public String editDataPage(Principal principal,Model model,@ModelAttribute("member") Member member){
        ProfileDto profileMyDto = new ProfileDto();

        String loginId = principal.getName();
        Member findMember = memberRepository.findByloginId(loginId).get();

        String homePhoneNumber = findMember.getHomePhoneNumber();
        String[] homePhoneNumberArr = homePhoneNumber.split(",");
        String phoneNumber = findMember.getPhoneNumber();
        String[] phoneNumberArr = phoneNumber.split(",");
        String birthday = findMember.getBirthday();
        String[] birthdayArr = birthday.split(",");

        if(findMember.getAddress() == null){
            findMember.setAddress(new Address("","",""));
            System.out.println("변경완료");
        }
        profileMyDto.setName(findMember.getName());
        profileMyDto.setLoginId(findMember.getLoginId());
        profileMyDto.setStreet(findMember.getAddress().getStreet());
        profileMyDto.setZipcode(findMember.getAddress().getZipcode());
        profileMyDto.setCity(findMember.getAddress().getCity());
        profileMyDto.setHomePhoneNumber(homePhoneNumberArr);
        profileMyDto.setPhoneNumber(phoneNumberArr);
        profileMyDto.setEmail(findMember.getEmail());
        profileMyDto.setBirthday(birthdayArr);


        model.addAttribute("member",profileMyDto);

        return "main/editdata";
    }

    @PutMapping("/update")
    public String editDataSavePage(Principal principal, @ModelAttribute("member") ProfileDto profileDto){

        memberService.updateProfile(principal.getName(),profileDto);

        return "redirect:/main/myPage";
    }

}
