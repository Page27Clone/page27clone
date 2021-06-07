package com.page27.project.controller;

import com.page27.project.domain.Member;
import com.page27.project.dto.ItemDto;
import com.page27.project.dto.OrderDto;
import com.page27.project.repository.ItemRepository;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.OrderRepository;
import com.page27.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/admin/changepassword")
    public String adminChangePassword(){
        return "admin/admin_changePassword";
    }

    @PutMapping("/admin/changepassword_ok")
    public @ResponseBody String changeAdminPasswordPage(Principal principal, @RequestParam("password")String newPassword){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//
//        System.out.println(user.getPassword());


//        Optional<Member> userEntityWrapper = memberRepository.findByloginId(loginId);
//        System.out.println("why");
//        System.out.println(memberRepository.findByloginId("here check : " + userEntityWrapper .get().getLoginId()));
//        Member userEntity = userEntityWrapper.get();
//        System.out.println("userEntity ID = " + userEntity.getLoginId());
//        System.out.println("userEntity Password = " + userEntity.getPassword());

        System.out.println(principal.getName());
        Optional<Member> loginMember = memberRepository.findByloginId(principal.getName());

        Member tempMember = loginMember.get();

        System.out.println(tempMember.getLoginId());
        System.out.println(tempMember.getPassword());
        System.out.println(newPassword);

        tempMember.setPassword(newPassword);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Long resultId = memberService.changePassword(tempMember.getId(), passwordEncoder.encode(newPassword));

        System.out.println(resultId);

        return "ajax 수정 완료";
    }

    @GetMapping("/admin/main")
    public String getMemberMainPage(Model model, @PageableDefault(size = 5) Pageable pageable){
        Page<Member> memberBoards = memberRepository.findAllByOrderByCreatedAt(pageable);
//        memberRepository.searchAll(pageable);
        Page<ItemDto> itemBoards = itemRepository.searchAllItem(pageable);
        Page<OrderDto> orderBoards = orderRepository.searchAllOrder(pageable);
        int allVisitCount = memberRepository.visitCountResult();


        System.out.println(allVisitCount);
        model.addAttribute("memberList",memberBoards);
        model.addAttribute("itemList",itemBoards);
        model.addAttribute("orderList",orderBoards);
        model.addAttribute("numVisitors",allVisitCount);

        return "admin/admin_main";
    }
}
