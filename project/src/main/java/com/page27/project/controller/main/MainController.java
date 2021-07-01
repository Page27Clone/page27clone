package com.page27.project.controller.main;

import com.page27.project.domain.*;
import com.page27.project.dto.*;
import com.page27.project.service.*;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MemberServiceImpl memberServiceImpl;
    private final DeliveryAddressServiceImpl deliveryAddressServiceImpl;
    private final OrderItemServiceImpl orderItemServiceImpl;
    private final BasketServiceImpl basketServiceImpl;
    private final MileageServiceImpl mileageServiceImpl;
    private final OrderServiceImpl orderServiceImpl;
    private final ItemServiceImpl itemServiceImpl;


    @GetMapping("main/mypage")
    public String getMyPage(Principal principal, Model model) {
        String loginId = principal.getName();

        MyPageDto myPageDto = memberServiceImpl.showMySimpleInfo(loginId);
        MyPageOrderStatusDto myPageOrderStatusDto = orderServiceImpl.showOrderStatus(loginId);

        model.addAttribute("member", myPageDto);
        model.addAttribute("orderStatus", myPageOrderStatusDto);

        return "main/mypage";
    }

    @GetMapping("/main/order")
    public String getOrderPage(Principal principal, @PageableDefault(size = 5) Pageable pageable, Model model, SearchOrder searchOrder) {
        String loginId = principal.getName();
        OrderMainPageDto orderPagingDto = orderServiceImpl.getOrderPagingDto(searchOrder, pageable, loginId);

        model.addAttribute("startPage", orderPagingDto.getHomeStartPage());
        model.addAttribute("endPage", orderPagingDto.getHomeEndPage());
        model.addAttribute("orderList", orderPagingDto.getMainPageOrderBoards());
        model.addAttribute("oMode", searchOrder.getOmode());
        model.addAttribute("firstDate", searchOrder.getFirstdate());
        model.addAttribute("lastDate", searchOrder.getLastdate());
//        order 페이지 내에서의 검색어

        model.addAttribute("omodeStatus", searchOrder.getOmode());
        model.addAttribute("firstdateStatus", searchOrder.getFirstdate());
        model.addAttribute("lastdateStatus", searchOrder.getLastdate());
//        마이페이지에서 order 페이지로 들어올때의 검색어
        return "main/order";
    }

    @ResponseBody
    @PatchMapping("/main/orderStatusChange/{id}")
    public String mainOrderStatusChangePage(@PathVariable Long id, @RequestParam OrderStatus status) {
        orderItemServiceImpl.chagneOrderStatus(id, status);

        return "주문상태 변경완료";
    }

    @GetMapping("/main/profile")
    public String editDataPage(Principal principal, Model model, @ModelAttribute("member") Member member) {
        String loginId = principal.getName();
        ProfileDto myProfileDto = memberServiceImpl.showProfileData(loginId);

        model.addAttribute("member", myProfileDto);

        return "main/editdata";
    }

    @PutMapping("/update")
    public String editDataPage(Principal principal, @ModelAttribute("member") ProfileDto profileDto) {

        memberServiceImpl.updateProfile(principal.getName(), profileDto);

        return "redirect:/main/mypage";
    }

    @GetMapping("/main/address")
    public String getAddressPage(Principal principal, Model model) {
        String loginId = principal.getName();
        List<DeliveryAddress> deliveryAddressList = deliveryAddressServiceImpl.getDeliveryAddressByLoginId(loginId);

        model.addAttribute("addressList", deliveryAddressList);

        return "main/address";
    }

    @GetMapping("/main/address/register")
    public String getRegisterPage() {
        return "main/register_address";
    }

    @PostMapping("/main/address/register_ok")
    public String registerAddressPage(Principal principal, @ModelAttribute AddressDto addressDto) {
        String loginId = principal.getName();

        deliveryAddressServiceImpl.registerAddress(loginId, addressDto);

        return "redirect:/main/address";
    }

    @ResponseBody
    @DeleteMapping("/main/address/delete")
    public String deleteAddressPage(@RequestParam(value = "addressIdList", required = false) List<Long> addressIdList) {
        for (Long addressId : addressIdList) {
            deliveryAddressServiceImpl.deleteAddressById(addressId);
        }

        return "주소 삭제 완료";
    }

    @GetMapping("/main/address/change/{id}")
    public String getChangeAddressPage(@PathVariable Long id, Model model) {
        AddressChangeDto addressChangeDto = deliveryAddressServiceImpl.showAddressToChange(id);

        model.addAttribute("address", addressChangeDto);

        return "main/change_address";
    }

    @PutMapping("/main/changeaddress_ok")
    public String changeAddressStatus(@ModelAttribute AddressChangeDto addressChangeDto) {

        deliveryAddressServiceImpl.updateDeliveryAddress(addressChangeDto.getId(), addressChangeDto);

        return "redirect:/main/address";
    }

    @GetMapping("/main/mileage")
    public String getMileagePage(Principal principal, Model model, @PageableDefault(size = 5) Pageable pageable) {
        String loginId = principal.getName();

        int totalMileage = mileageServiceImpl.getTotalMileage(loginId);
        int totalUsedMileage = mileageServiceImpl.getTotalUsedMileage(loginId);

        MileagePageDto mileagePagingDto = mileageServiceImpl.getMileagePagingDto(loginId, pageable);

        model.addAttribute("totalmileage", totalMileage);
        model.addAttribute("usedmileage", totalUsedMileage);
        model.addAttribute("restmileage", totalMileage - totalUsedMileage);

        model.addAttribute("startPage", mileagePagingDto.getHomeStartPage());
        model.addAttribute("endPage", mileagePagingDto.getHomeEndPage());

        model.addAttribute("mileageList", mileagePagingDto.getMileageBoards());

        return "main/mileage";
    }

    @GetMapping("/main/category/{firstCategory}/{secondCategory}")
    public String getCategoryPage(@PathVariable String firstCategory, @PathVariable String secondCategory, @PageableDefault(size = 12) Pageable pageable, Model model) {
        ItemPageDto itemPagingDto = itemServiceImpl.getItemPagingDtoByCategory(pageable, firstCategory, secondCategory);

        model.addAttribute("startPage", itemPagingDto.getHomeStartPage());
        model.addAttribute("endPage", itemPagingDto.getHomeEndPage());
        model.addAttribute("itemList", itemPagingDto.getItemBoards());
        model.addAttribute("categoryName", secondCategory);
        model.addAttribute("firstCategory", firstCategory);
        model.addAttribute("secondCategory", secondCategory);

        return "main/category";
    }

    @GetMapping("/main/basket")
    public String getBasketPage(Principal principal, Model model) {
        String loginId = principal.getName();

        MyPageDto myPageDto = memberServiceImpl.showMySimpleInfo(loginId);
        Member member = memberServiceImpl.findMemberByLoginId(loginId);
        List<Basket> basketList = basketServiceImpl.findAllBasketByMemberId(member.getId());
        List<ItemDto> allItemInBasket = itemServiceImpl.getAllItemInBasket(basketList);

        model.addAttribute("member", myPageDto);
        model.addAttribute("itemList", allItemInBasket);
        model.addAttribute("basketList", basketList);

        return "main/basket";
    }
//        회원을 아이디를 통해 찾는다
//        회원 PK를 통해 Basket 테이블에서 각 row를 찾는다.
//        찾은 Basket PK를 활용해 해당 아이템의 PK를 찾고 itemRepository에서 이 PK를 가지고 아이템 정보를 가져온다.
//        이후 이 정보는 List에 담겨서 보내지는 것이다.

    @ResponseBody
    @PatchMapping("/main/basket/changequantity/{basketId}/{itemQuantity}")
    public String changeQuantityInBasketPage(@PathVariable Long basketId, @PathVariable int itemQuantity) {
        basketServiceImpl.changeBasketItemQuantity(basketId, itemQuantity);

        return "수량 변경 완료";
    }

    @ResponseBody
    @DeleteMapping("/main/basket/remove/{basketId}")
    public String deleteItemInBasketPage(@PathVariable Long basketId) {
        basketServiceImpl.deleteBasketById(basketId);

        return "장바구니 상품 삭제 완료";
    }

    @ResponseBody
    @DeleteMapping("/main/basket/removeitems")
    public String deleteItemsInBaketPage(@RequestParam(value = "itemList", required = false) List<Long> itemList) {
        for (int i = 0; i < itemList.size(); i++) {
            basketServiceImpl.deleteBasketById(itemList.get(i));
        }

        return "장바구니 상품 삭제 완료";
    }

    @ResponseBody
    @DeleteMapping("/main/basket/removeall")
    public String deleteAllItemsInBasketPage(Principal principal) {
        Member member = memberServiceImpl.findMemberByLoginId(principal.getName());
        List<Basket> basketList = basketServiceImpl.findAllBasketByMemberId(member.getId());

        for (int i = 0; i < basketList.size(); i++) {
            basketServiceImpl.deleteBasketById(basketList.get(i).getId());
        }

        return "장바구니 비우기 완료";
    }

    @GetMapping("/main/product/{itemIdx}")
    public String getProductPage(@PathVariable Long itemIdx, Model model) {
        ItemDetailDto itemDetailDto = itemServiceImpl.getItemDetailDto(itemIdx);

        model.addAttribute("item", itemDetailDto);

        return "main/product";
    }

    @PostMapping("/main/product/basketadd_ok")
    public String addItemInBasketPage(Principal principal, ItemToBasketDto itemToBasketDto) {
        int quantity = Integer.parseInt(itemToBasketDto.getQuantity());
        Long itemIdx = Long.parseLong(itemToBasketDto.getItem_idx());
        String color = itemToBasketDto.getItem_color();

        itemServiceImpl.moveItemToBasket(principal.getName(), itemIdx, color, quantity);

        return "redirect:/main/basket";
    }

    @PostMapping("/main/payment")
    public String getPaymentDataPage(Principal principal, Model model, @RequestParam(value = "itemlist") String itemlist, @RequestParam(value = "where") String where) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        if (where.equals("basket")) {
            itemDtoList = basketServiceImpl.basketListToPayment(itemlist);
        } else if (where.equals("product")) {
            itemDtoList = itemServiceImpl.itemToPayment(itemlist);
        }

        String loginId = principal.getName();
        MyPageDto myPageDto = memberServiceImpl.showMySimpleInfo(loginId);
        List<DeliveryAddress> deliveryAddressList = deliveryAddressServiceImpl.getDeliveryAddressByLoginId(loginId);

        model.addAttribute("orderList", itemDtoList);
        model.addAttribute("addressList", deliveryAddressList);
        model.addAttribute("member", myPageDto);

        return "main/payment";
    }

    @ResponseBody
    @PostMapping("/main/payment/changeaddress/{deliveryAddressId}")
    public DeliveryAddress chnageDeliveryAddressInfo(@PathVariable Long deliveryAddressId) {
        DeliveryAddress findDeliveryAddress = deliveryAddressServiceImpl.findAddressById(deliveryAddressId);

        return findDeliveryAddress;
    }

    @PostMapping("main/payment_ok")
    public String doPaymentPage(Principal principal, @RequestParam(value = "orderiteminfo") String orderItemInfo, PaymentAddressDto paymentAddressDto, PaymentPriceDto paymentPriceDto, Model model) {
        Member member = memberServiceImpl.findMemberByLoginId(principal.getName());
        ItemListToOrderDto itemListToOrderDto = itemServiceImpl.itemToOrder(orderItemInfo);
        MyPageDto myPageDto = memberServiceImpl.showMySimpleInfo(principal.getName());
        paymentPriceDto.setEarnMileage(paymentPriceDto.getTobepaid_price() * 0.01);

        orderServiceImpl.doOrder(member.getId(), itemListToOrderDto.getItemList(), itemListToOrderDto.getItemCountList(), paymentAddressDto, paymentPriceDto);

        model.addAttribute("member", myPageDto);
        model.addAttribute("payment", paymentPriceDto);
        model.addAttribute("address", paymentAddressDto);

        return "main/payment_complete";
    }

    @GetMapping("/main/index")
    public String getMainPage(Model model) {
        List<Item> mainCarouselList = itemServiceImpl.getMainCarouselItemList();

        List<WeeklyBestDto> outerWeeklyBestItem = itemServiceImpl.getOuterWeeklyBestItem();
        List<WeeklyBestDto> bottomWeeklyBestItem = itemServiceImpl.getBottomWeeklyBestItem();
        List<WeeklyBestDto> shirtsWeeklyBestItem = itemServiceImpl.getShirtsWeeklyBestItem();
        List<WeeklyBestDto> shoesWeeklyBestItem = itemServiceImpl.getShoesWeeklyBestItem();
        List<WeeklyBestDto> sleeveTopWeeklyBestItem = itemServiceImpl.getSleeveTopWeeklyBestItem();
        List<WeeklyBestDto> topKnitWeeklyBestItem = itemServiceImpl.getTopKnitWeeklyBestItem();

        List<WeeklyBestDto> newArrivalItemList = itemServiceImpl.getNewArrivalItem();


        model.addAttribute("mainCarousel", mainCarouselList);

        model.addAttribute("outerList", outerWeeklyBestItem);
        model.addAttribute("topList", sleeveTopWeeklyBestItem);
        model.addAttribute("shirtList", shirtsWeeklyBestItem);
        model.addAttribute("knitList", topKnitWeeklyBestItem);
        model.addAttribute("bottomList", bottomWeeklyBestItem);
        model.addAttribute("shoesList", shoesWeeklyBestItem);

        model.addAttribute("newarrivals", newArrivalItemList);

        return "main/index";
    }

    @GetMapping("/main/restrict")
    public String getRestrictPage() {
        return "main/restrict";
    }

    @ResponseBody
    @PostMapping("/main/register/doublecheck")
    public String idDoubleCheckPage(@RequestParam(value = "registerId") String registerId) {
        if (memberServiceImpl.doubleCheckId(registerId)) {
            return "사용할 수 없는 아이디입니다.";
        } else {
            return "사용할 수 있는 아이디입니다.";
        }
    }

    @ResponseBody
    @DeleteMapping("/main/withdrawal")
    public String withdrawalMember(HttpServletRequest request, Principal principal, @RequestParam(value = "user_pw") String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String loginId = principal.getName();
        Member findMember = memberServiceImpl.findMemberByLoginId(loginId);

        boolean result = passwordEncoder.matches(password, findMember.getPassword());

        if (result) {
            memberServiceImpl.deleteMemberByLoginId(loginId);
            HttpSession session = request.getSession();
            session.invalidate();
            return "정상적으로 회원탈퇴되었습니다.";
        } else {
            return "비밀번호가 올바르지 않습니다";
        }
    }


}
