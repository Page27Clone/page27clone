package com.page27.project.controller.admin;

import com.page27.project.domain.*;
import com.page27.project.dto.*;
import com.page27.project.repository.ItemRepository;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.OrderRepository;
import com.page27.project.service.ItemServiceImpl;
import com.page27.project.service.MemberServiceImpl;
import com.page27.project.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberServiceImpl memberServiceImpl;
    private final ItemServiceImpl itemServiceImpl;
    private final OrderServiceImpl orderServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @GetMapping("/admin/changepassword")
    public String adminChangePassword() {
        return "admin/admin_changePassword";
    }

    @PutMapping("/admin/changepassword_ok")
    public @ResponseBody
    String changeAdminPasswordPage(Principal principal, @RequestParam("password") String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member findMember = memberServiceImpl.findMemberByLoginId(principal.getName());

        findMember.setPassword(newPassword);
        Long resultId = memberServiceImpl.changePassword(findMember.getId(), passwordEncoder.encode(newPassword));

        return "관리자 비밀번호 수정 완료";
    }

    @GetMapping("/admin/main")
    public String getMemberMainPage(Model model, @PageableDefault(size = 4) Pageable pageable) {
        Page<Member> memberBoards = memberServiceImpl.findAllMemberByOrderByCreatedAt(pageable);
        Page<ItemDto> itemBoards = itemServiceImpl.findAllItem(pageable);
        Page<OrderDto> orderBoards = orderServiceImpl.findAllOrder(pageable);
        int allVisitCount = memberServiceImpl.getVisitCount();

        model.addAttribute("memberList", memberBoards);
        model.addAttribute("itemList", itemBoards);
        model.addAttribute("orderList", orderBoards);
        model.addAttribute("numVisitors", allVisitCount);

        return "admin/admin_main";
    }

    @GetMapping("/admin/register")
    public String getRegisterItemPage() {
        return "admin/admin_registerGoods";
    }

    @PostMapping("/admin/register")
    public String requestupload2(MultipartHttpServletRequest mtfRequest, @RequestParam("cmode1") String firstCategory
            , @RequestParam("cmode2") String secondCategory
            , @RequestParam("cmode3") String thirdCategory
            , @RequestParam("item_name") String itemName
            , @RequestParam("price") int itemPrice
            , @RequestParam("salestatus") String saleStatus
            , @RequestParam("info") String itemInfo
            , @RequestParam("color") String itemColor
            , @RequestParam("size") String itemSize
            , @RequestParam("stock_quantity") int itemQuantity
            , @RequestParam("fabric") String itemFabric
            , @RequestParam("model") String itemModel
    ) {

        String folderPath = "C:\\Users\\skyey\\Desktop\\페이지27 프로젝트\\프로젝트\\project\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\" + firstCategory + "\\" + secondCategory + "\\" + itemName + "\\";

        File newFile = new File(folderPath);
        if (newFile.mkdirs()) {
            logger.info("directory make ok");
        } else {
            logger.warn("directory can't make");
        }

        List<MultipartFile> fileList = mtfRequest.getFiles("upload_image");
        Long newItemIdx = itemServiceImpl.getMaxItemIdx();

        for (int i = 0; i < fileList.size(); i++) {
            String originFileName = fileList.get(i).getOriginalFilename();
            String safeFile = folderPath + originFileName;
            System.out.println("check here");
            System.out.println(folderPath);
            System.out.println(originFileName);

            Item item = new Item(firstCategory, secondCategory, thirdCategory, itemName, itemPrice, itemInfo, itemColor, itemFabric, itemModel, itemSize, itemQuantity, safeFile, saleStatus, newItemIdx + 1, true);

            if (i == 0) {
                item.setRep(true);
            } else {
                item.setRep(false);
            }
            itemServiceImpl.saveItem(item);
            try {
                fileList.get(i).transferTo(new File(safeFile));
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/admin/itemList";
    }

    @GetMapping("/admin/itemList")
    public String itemListPage(Model model, @PageableDefault(size = 5) Pageable pageable, SearchItem searchItem) {
        ItemPageDto itemPageDto = new ItemPageDto();
        if (searchItem.getItem_name() == null) {
            itemPageDto = itemServiceImpl.findAllItemByPaging(pageable);
        } else {
            itemPageDto = itemServiceImpl.findAllItemByConditionByPaging(searchItem, pageable);
        }
        Page<ItemDto> itemBoards = itemPageDto.getItemBoards();
        int homeStartPage = itemPageDto.getHomeStartPage();
        int homeEndPage = itemPageDto.getHomeEndPage();

        model.addAttribute("productList", itemBoards);
        model.addAttribute("startPage", homeStartPage);
        model.addAttribute("endPage", homeEndPage);

        model.addAttribute("saleStatus", searchItem.getSalestatus());
        model.addAttribute("firstCategory", searchItem.getCmode());
        model.addAttribute("itemName", searchItem.getItem_name());

        return "admin/admin_Goodslist";
    }

    @ResponseBody
    @PatchMapping("/admin/itemList/onsale")
    public String itemStatusOnSalePage(@RequestBody List<Map<String, String>> allData) {
        for (Map<String, String> temp : allData) {
            itemServiceImpl.changeItemStatusOnSale(temp.get("itemIdx"), temp.get("itemColor"));
        }
        return "상품 상태 판매로 변경완료";
    }

    @ResponseBody
    @PatchMapping("/admin/itemList/soldout")
    public String itemStatusSoldOutPage(@RequestBody List<Map<String, String>> allData) {
        for (Map<String, String> temp : allData) {
            itemServiceImpl.changeItemStatusSoldOut(temp.get("itemIdx"), temp.get("itemColor"));
        }
        return "상품 상태 품절로 변경완료";
    }

    @ResponseBody
    @DeleteMapping("/admin/itemList/remove")
    public String itemdeletePage(@RequestBody List<Map<String, String>> allData) {
        for (Map<String, String> temp : allData) {
            itemServiceImpl.deleteItemById(temp.get("itemIdx"), temp.get("itemColor"));
        }
        return "상품 삭제 완료";
    }

    @GetMapping("/admin/userList")
    public String pageList(Model model, @PageableDefault(size = 4) Pageable pageable, SearchMember searchMember) {
        MemberPageDto memberPageDto = new MemberPageDto();

        if (searchMember.getSearchKeyword() == null) {
            memberPageDto = memberServiceImpl.findAllMemberByPaging(pageable);
        } else {
            memberPageDto = memberServiceImpl.findAllMemberByConditionByPaging(searchMember, pageable);
        }

        int homeStartPage = memberPageDto.getHomeStartPage();
        int homeEndPage = memberPageDto.getHomeEndPage();
        Page<MemberDto> memberBoards = memberPageDto.getMemberBoards();

        model.addAttribute("startPage", homeStartPage);
        model.addAttribute("endPage", homeEndPage);
        model.addAttribute("memberList", memberBoards);
        model.addAttribute("searchCondition", searchMember.getSearchCondition());
        model.addAttribute("searchKeyword", searchMember.getSearchKeyword());

        return "admin/admin_userlist";
    }

    @GetMapping("/admin/userList/user/{id}")
    public String pageUser(@PathVariable Long id, Model model) {
        model.addAttribute("Member", memberServiceImpl.findMemberById(id));

        return "admin/admin_user";
    }

    @ResponseBody
    @DeleteMapping("/admin/userList/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberServiceImpl.deleteById(id);

        return "회원 삭제 완료";
    }

    @ResponseBody
    @DeleteMapping("/admin/userList")
    public String deleteChecked(@RequestParam(value = "idList", required = false) List<Long> idList) {
        int size = idList.size();

        for (int i = 0; i < size; i++) {
            memberServiceImpl.deleteById(idList.get(i));
        }
        return "선택된 회원 삭제 완료";
    }

    @GetMapping("/admin/orderList")
    public String getOrderPage(Model model, @PageableDefault(size = 4) Pageable pageable, SearchOrder searchOrder) {

        OrderPageDto orderPageDto = new OrderPageDto();

        if (StringUtils.isEmpty(searchOrder.getFirstdate()) && StringUtils.isEmpty(searchOrder.getLastdate()) && StringUtils.isEmpty(searchOrder.getSinput())) {
            orderPageDto = orderServiceImpl.findAllOrderByPaging(pageable);
        } else {
            orderPageDto = orderServiceImpl.findAllOrderByConditionByPaging(searchOrder, pageable);
        }

        Page<OrderDto> orderBoards = orderPageDto.getOrderBoards();
        int homeStartPage = orderPageDto.getHomeStartPage();
        int homeEndPage = orderPageDto.getHomeEndPage();

        model.addAttribute("orderList", orderBoards);
        model.addAttribute("startPage", homeStartPage);
        model.addAttribute("endPage", homeEndPage);

        model.addAttribute("firstDate", searchOrder.getFirstdate());
        model.addAttribute("lastDate", searchOrder.getLastdate());
        model.addAttribute("oMode", searchOrder.getOmode());
        model.addAttribute("sMode", "buyer");
        model.addAttribute("sInput", searchOrder.getSinput());
        model.addAttribute("oModeStatus", searchOrder.getOmode());

        return "admin/admin_order";

    }

    @ResponseBody
    @PatchMapping("/admin/orderList1/{id}")
    public String orderStatusChangePage(@PathVariable Long id, @RequestParam OrderStatus status) {
        orderServiceImpl.changeOrderStatus(id, status);

        return "주문 상품 상태 변경완료";
    }
}