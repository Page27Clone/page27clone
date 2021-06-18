package com.page27.project.controller.main;

import com.page27.project.domain.*;
import com.page27.project.dto.*;
import com.page27.project.repository.*;
import com.page27.project.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final AddressService addressService;
    private final ItemRepository itemRepository;
    private final BasketService basketService;

    //이건 테스트
    private final MileageRepository mileageRepository;
    private final MileageService mileageService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final BasketRepository basketRepository;


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
    public String getAddressPage(Principal principal, Model model){
        List<DeliveryAddress> deliveryAddressList = addressService.getDeliveryAddress(principal.getName());
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

    @GetMapping("/main/mileage")
    public String getMileagePage(Principal principal,Model model,@PageableDefault(size=1) Pageable pageable){

        String loginId = principal.getName();
        Member member = memberRepository.findByloginId(loginId).get();

        int totalMileage = mileageService.getTotalMileage(loginId);
        int totalUsedMileage = mileageService.getTotalUsedMileage(loginId);

        Page<Mileage> mileageBoards = mileageRepository.findAllByMember(member,pageable);
        int homeStartPage = Math.max(1,mileageBoards.getPageable().getPageNumber() - 4);
        int homeEndPage = Math.min(mileageBoards.getTotalPages(),mileageBoards.getPageable().getPageNumber() + 4);

        model.addAttribute("totalmileage",totalMileage);
        model.addAttribute("usedmileage",totalUsedMileage);
        model.addAttribute("restmileage",totalMileage - totalUsedMileage);

        model.addAttribute("startPage",homeStartPage);
        model.addAttribute("endPage",homeEndPage);

        model.addAttribute("mileageList",mileageBoards);

        return "main/mileage";
    }

    @GetMapping("/main/category")
    public String categoryTestPage(){
        return "main/category";
    }

    @GetMapping("/main/category/{firstCategory}/{secondCategory}")
    public String getCategoryPage(@PathVariable String firstCategory, @PathVariable String secondCategory, @PageableDefault(size = 8) Pageable pageable, Model model){

        System.out.println("여기는 들어옴!!");
        Page<ItemDto> itemBoards = itemRepository.findAllItem(pageable, firstCategory, secondCategory);
        System.out.println("이것도 사이즈 체크 : " + itemBoards.getTotalElements());//이거 사이즈 받는거같은데
        System.out.println("이것도 사이즈 체크 : " + itemBoards.getTotalPages());

        int homeStartPage = Math.max(1,itemBoards.getPageable().getPageNumber() - 4);
        int homeEndPage = Math.min(itemBoards.getTotalPages(),itemBoards.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage",homeStartPage);
        model.addAttribute("endPage",homeEndPage);
        model.addAttribute("itemList",itemBoards);
        model.addAttribute("categoryname",secondCategory);
        model.addAttribute("firstCategory",firstCategory);
        model.addAttribute("secondCategory",secondCategory);

        return "main/category";
    }

    @GetMapping("/main/basket")
    public String getBasketPage(Principal principal, Model model){
        String loginId = principal.getName();

        BasketMemberMileageDto memberMileage = basketService.findMemberMileage(loginId);

        Member member = memberRepository.findByloginId(loginId).get();
//        회원을 아이디를 통해 찾는다
        List<Basket> basketList = basketRepository.findAllByMemberId(member.getId());
//        회원 PK를 통해 Basket 테이블에서 각 row를 찾는다.
        List<ItemDto> allItemInBasket = new ArrayList<ItemDto>();
        for(int i= 0; i< basketList.size();i++){
            Long itemId = basketList.get(i).getItem().getId();
            allItemInBasket.add(itemRepository.findAllItemInBasket(itemId));
        }
//        찾은 Basket PK를 활용해 해당 아이템의 PK를 찾고 itemRepository에서 이 PK를 가지고 아이템 정보를 가져온다.
//        이후 이 정보는 List에 담겨서 보내지는 것이다.
        model.addAttribute("member",memberMileage);
        model.addAttribute("itemList",allItemInBasket);
        model.addAttribute("basketId",basketList);

        return "main/basket";
    }


}
