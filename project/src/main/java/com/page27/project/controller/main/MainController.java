package com.page27.project.controller.main;

import com.page27.project.domain.*;
import com.page27.project.dto.*;
import com.page27.project.repository.*;
import com.page27.project.service.AddressService;
import com.page27.project.service.MemberService;
import com.page27.project.service.MileageService;
import com.page27.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final AddressService addressService;

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

        if(findMember.getMemberAddress() == null){
            findMember.setMemberAddress(new MemberAddress("","",""));
            System.out.println("변경완료");
        }
        profileMyDto.setName(findMember.getName());
        profileMyDto.setLoginId(findMember.getLoginId());
        profileMyDto.setStreet(findMember.getMemberAddress().getStreet());
        profileMyDto.setZipcode(findMember.getMemberAddress().getZipcode());
        profileMyDto.setCity(findMember.getMemberAddress().getCity());
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

    @GetMapping("/main/address")
    public String getAddressPage(Model model){
        List<DeliveryAddress> deliveryAddressList = addressService.getDeliveryAddress();
        System.out.println(deliveryAddressList.size());
        model.addAttribute("addressList",deliveryAddressList);

        return "main/address";
    }

    @GetMapping("/main/address/register")
    public String getRegisterPage(){
        return "main/register_address";
    }

    @PostMapping("/main/address/register_ok")
    public String registerAddressPage(Principal principal, @ModelAttribute AddressDto addressDto){

        addressService.registerAddress(principal.getName(),addressDto);
        return "redirect:/main/address";
    }

    @ResponseBody
    @DeleteMapping("/main/address/delete")
    public String deleteAddressPage(@RequestParam(value = "addressIdList",required = false) List<Long> addressIdList) {
        for(int i =0 ; i< addressIdList.size();i++){
            addressService.deleteAddress(addressIdList.get(i));
        }

        return "주소 삭제 완료";
    }

    @GetMapping("/main/address/change/{id}")
    public String changeAddressPage(@PathVariable Long id, Model model){
        DeliveryAddress addressById = addressService.findAddressById(id);

        String[] addressHomePhoneNumber = addressById.getAddressHomePhoneNumber().split(",");
        String[] addressPhoneNumber = addressById.getAddressPhoneNumber().split(",");

        AddressChangeDto addressChangeDto = new AddressChangeDto();

        addressChangeDto.setId(addressById.getId());
        addressChangeDto.setPlaceName(addressById.getPlaceName());
        addressChangeDto.setRecipient(addressById.getRecipient());
        addressChangeDto.setCity(addressById.getCity());
        addressChangeDto.setZipcode(addressById.getZipcode());
        addressChangeDto.setStreet(addressById.getStreet());
        addressChangeDto.setAddressPhoneNumber(addressPhoneNumber);
        addressChangeDto.setAddressHomePhoneNumber(addressHomePhoneNumber);

        model.addAttribute("address",addressChangeDto);

        return "main/change_address";
    }

    @PutMapping("/main/changeaddress_ok")
    public String changeAddressStatus(@ModelAttribute AddressChangeDto addressChangeDto){

        addressService.updateDeliveryAddress(addressChangeDto.getId(),addressChangeDto);

        return "redirect:/main/address";
    }


}
