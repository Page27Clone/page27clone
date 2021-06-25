package com.page27.project.controller.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.page27.project.domain.*;
import com.page27.project.dto.*;
import com.page27.project.repository.*;
import com.page27.project.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final ItemService itemService;


    @GetMapping("main/mypage")
    public String getMyPage(Principal principal , Model model){
        MyPageDto myPageDto = new MyPageDto();
        String loginId = principal.getName();
        Member member = memberRepository.findByloginId(loginId).get();
        myPageDto.setName(member.getName());
        myPageDto.setGrade(member.getMemberGrade());
        System.out.println("here check : " + member.getMemberGrade());
        int totalMileage = mileageService.getTotalMileage(loginId);
        int totalUsedMileage = mileageService.getTotalUsedMileage(loginId);
        myPageDto.setMileage(totalMileage - totalUsedMileage);

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
        model.addAttribute("omode",searchOrder.getOmode());
        model.addAttribute("firstdate",searchOrder.getFirstdate());
        model.addAttribute("lastdate",searchOrder.getLastdate());
        model.addAttribute("omodeStatus",searchOrder.getOmode());
        model.addAttribute("firstdateStatus",searchOrder.getFirstdate());
        model.addAttribute("lastdateStatus",searchOrder.getLastdate());

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
        AddressDto addressById = addressService.findAddressById(id);

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
        model.addAttribute("basketList",basketList);

        return "main/basket";
    }

    @ResponseBody
    @PatchMapping("/main/basket/changequantity/{basketId}/{itemQuantity}")
    public String changeQuantityInBasketPage(@PathVariable Long basketId, @PathVariable int itemQuantity){
        Basket basket = basketRepository.findById(basketId).get();
        basket.setBasketCount(itemQuantity);
        basketRepository.save(basket);

        return "수량 변경 완료";
    }

    @ResponseBody
    @DeleteMapping("/main/basket/remove/{basketId}")
    public String deleteItemInBasketPage(@PathVariable Long basketId){
        System.out.println("삭제할 상품 장바구니 아이디 : " + basketId);
        basketRepository.deleteById(basketId);

        return "장바구니 상품 삭제 완료";
    }

    @ResponseBody
    @DeleteMapping("/main/basket/removeitems")
    public String deleteItemsInBaketPage(@RequestParam(value = "itemList",required = false)List<Long> itemList){
        System.out.println("here check : " + itemList.size());
        for(int i = 0;i<itemList.size();i++){
            basketRepository.deleteById(itemList.get(i));
        }
        return "장바구니 상품 삭제 완료";
    }

    @ResponseBody
    @DeleteMapping("/main/basket/removeall")
    public String deleteAllItemsInBasketPage(Principal principal){
        Member member = memberRepository.findByloginId(principal.getName()).get();

        List<Basket> basketList = basketRepository.findAllByMemberId(member.getId());
        System.out.println("basketList size : " + basketList.size());

        for(int i = 0; i< basketList.size();i++){
            basketRepository.deleteById(basketList.get(i).getId());
        }

        return "장바구니 비우기 완료";
    }

    @GetMapping("/main/product/{itemIdx}")
    public String getProductPage(@PathVariable Long itemIdx, Model model){
        ItemDetailDto itemDetailDto = itemService.getItemDetailDto(itemIdx);
        model.addAttribute("item",itemDetailDto);

        System.out.println(itemDetailDto.getImgMainUrl());

        return "main/product";
    }

    @PostMapping("/main/product/basketadd_ok")
    public String addItemInBasketPage(Principal principal, ItemToBasket itemToBasket){
        String loginId = principal.getName();
        int quantity = Integer.parseInt(itemToBasket.getQuantity());
        Long itemIdx = Long.parseLong(itemToBasket.getItem_idx());
        String color = itemToBasket.getItem_color();

        itemService.moveItemToBasket(loginId,itemIdx,color,quantity);
        return "redirect:/main/basket";
    }

    @PostMapping("/main/payment")
    public String getPaymentDataPage(Principal principal , ItemToBasket itemToBasket, Model model,@RequestParam(value = "itemlist") String itemlist, @RequestParam(value = "where") String where){

        List<ItemDto> itemDtoList = new ArrayList<>();

        if(where.equals("basket")) {
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (JsonArray) jsonParser.parse(itemlist);

            List<Long> basketIdList = new ArrayList<>();
            List<Integer> basketQuantityList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject object = (JsonObject) jsonArray.get(i);
                String id = object.get("id").getAsString();
                basketIdList.add(Long.parseLong(id));
                String quantity = object.get("quantity").getAsString();
                basketQuantityList.add(Integer.parseInt(quantity));
            }

            for (int i = 0; i < basketIdList.size(); i++) {
                Long itemId = basketRepository.findById(basketIdList.get(i)).get().getItem().getId();
                itemDtoList.add(itemRepository.findAllItemInBasket(itemId));
            }
            model.addAttribute("orderList",itemDtoList);
        }
        else if(where.equals("product")) {
            ItemDto itemDto = new ItemDto();

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (JsonArray) jsonParser.parse(itemlist);

            JsonObject object = (JsonObject) jsonArray.get(0);
            String id = object.get("idx").getAsString();
            Long itemIdx = Long.parseLong(id);
            String color = object.get("color").getAsString();
            String quantity = object.get("quantity").getAsString();
            int orderCount = Integer.parseInt(quantity);

            Item byItemIdxAndColorAndRep = itemRepository.findByItemIdxAndColorAndRep(itemIdx, color, true);
            itemDto.setItemIdx(byItemIdxAndColorAndRep.getItemIdx());
            itemDto.setItemName(byItemIdxAndColorAndRep.getItemName());
            itemDto.setColor(byItemIdxAndColorAndRep.getColor());
            itemDto.setBasketCount(orderCount);
            itemDto.setId(byItemIdxAndColorAndRep.getId());
            itemDto.setPrice(byItemIdxAndColorAndRep.getPrice());
            itemDto.setImgUrl(byItemIdxAndColorAndRep.getImgUrl());

            itemDtoList.add(itemDto);

            model.addAttribute("orderList",itemDtoList);
        }

        String loginId = principal.getName();
        BasketMemberMileageDto memberMileage = basketService.findMemberMileage(loginId);
        List<DeliveryAddress> deliveryAddressList = addressService.getDeliveryAddress(loginId);

        model.addAttribute("addressList",deliveryAddressList);
        model.addAttribute("member",memberMileage);


        return "main/payment";
    }

    @ResponseBody
    @PostMapping("/main/payment/changeaddress/{deliveryAddressId}")
    public AddressDto chnageDeliveryAddressInfo(@PathVariable Long deliveryAddressId){
        AddressDto addressById = addressService.findAddressById(deliveryAddressId);

        return addressById;
    }

    @PostMapping("main/payment_ok")
    public String doPaymentPage(Principal principal,@RequestParam(value = "orderiteminfo") String orderItemInfo,PaymentAddressDto paymentAddressDto,PaymentPriceDto paymentPriceDto,Model model){
        String loginId = principal.getName();
        Member member = memberRepository.findByloginId(loginId).get();

        System.out.println("here check : " + orderItemInfo);

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(orderItemInfo);

        List<Long> itemList = new ArrayList<>();
        List<Integer> itemCountList = new ArrayList<>();

        for(int i= 0; i< jsonArray.size();i++) {
            JsonObject object = (JsonObject) jsonArray.get(i);
            String item_idx = object.get("item_idx").getAsString();
            String item_color = object.get("item_color").getAsString();
            String item_quantity = object.get("item_quantity").getAsString();

            Long itemIdx = Long.parseLong(item_idx);
            int itemOrderCount = Integer.parseInt(item_quantity);

            Item findItem = itemRepository.findByItemIdxAndColorAndRep(itemIdx, item_color, true);

            itemList.add(findItem.getId());
            itemCountList.add(itemOrderCount);
        }
        orderService.doOrder(member.getId(), itemList, itemCountList,paymentAddressDto,paymentPriceDto);

        BasketMemberMileageDto memberMileage = basketService.findMemberMileage(loginId);

        paymentPriceDto.setEarnMileage(paymentPriceDto.getTobepaid_price() * 0.01);
        model.addAttribute("member",memberMileage);
        model.addAttribute("payment",paymentPriceDto);
        model.addAttribute("address",paymentAddressDto);

        return "main/payment_complete";
    }

    @GetMapping("/main/index")
    public String getMainPage(Model model){

        List<Item> mainCarouselList = new ArrayList<>();

        Item firstItem = itemRepository.findByItemIdxAndColorAndRep(94L, "블루", true);
        Item seconodItem = itemRepository.findByItemIdxAndColorAndRep(95L, "블랙", true);
        Item thirdItem = itemRepository.findByItemIdxAndColorAndRep(96L, "네이비", true);
        Item fourthItem = itemRepository.findByItemIdxAndColorAndRep(97L, "블랙 M size", true);
        Item fifthItem = itemRepository.findByItemIdxAndColorAndRep(98L, "아이보리", true);

        mainCarouselList.add(firstItem);
        mainCarouselList.add(seconodItem);
        mainCarouselList.add(thirdItem);
        mainCarouselList.add(fourthItem);
        mainCarouselList.add(fifthItem);

        List<WeeklyBestDto> outerList = itemRepository.findWeeklyBestItem("outer","jacket", true);
        List<WeeklyBestDto> topList = itemRepository.findWeeklyBestItem("top","longsleeve", true);
        List<WeeklyBestDto> shirtList = itemRepository.findWeeklyBestItem("shirts","basic", true);
        List<WeeklyBestDto> knitList = itemRepository.findWeeklyBestItem("top","knit", true);
        List<WeeklyBestDto> bottomList = itemRepository.findWeeklyBestItem("bottom","cotton", true);
        List<WeeklyBestDto> shoesList = itemRepository.findWeeklyBestItem("shoes","shoes", true);

        List<WeeklyBestDto> newArrivalList = itemRepository.findNewArrivalItem("outer","jacket",true);
        for(int i = 0; i< newArrivalList.size();i++){
            newArrivalList.get(i).setMileage(newArrivalList.get(i).getPrice() / 100);
        }

        model.addAttribute("mainCarousel",mainCarouselList);

        model.addAttribute("outerList",outerList);
        model.addAttribute("topList",topList);
        model.addAttribute("shirtList",shirtList);
        model.addAttribute("knitList",knitList);
        model.addAttribute("bottomList",bottomList);
        model.addAttribute("shoesList",shoesList);

        model.addAttribute("newarrivals",newArrivalList);

        return "main/index";
    }

    @GetMapping("/main/restrict")
    public String getRestrictPage(){
        return "main/restrict";
    }

    @ResponseBody
    @PostMapping("/main/register/doublecheck")
    public String idDoubleCheckPage(@RequestParam(value = "registerId") String registerId){
        if(memberService.doubleCheckId(registerId)){
            return "사용할 수 없는 아이디입니다.";
        }
        else{
            return "사용할 수 있는 아이디입니다.";
        }
    }

    @ResponseBody
    @DeleteMapping("/main/withdrawal")
    public String withdrawalMember(HttpServletRequest request,Principal principal, @RequestParam(value = "user_pw") String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String loginId = principal.getName();
        Member findMember = memberRepository.findByloginId(loginId).get();

        System.out.println(findMember.getPassword());
        boolean result = passwordEncoder.matches(password,findMember.getPassword());

        if(result){
            memberService.deleteMemberByLoginId(loginId);
            HttpSession session = request.getSession();
            session.invalidate();

            return "정상적으로 회원탈퇴되었습니다.";
        }else{
            return "비밀번호가 올바르지 않습니다";
        }
    }



}
