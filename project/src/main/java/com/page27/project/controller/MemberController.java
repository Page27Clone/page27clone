package com.page27.project.controller;

import com.page27.project.domain.Member;
import com.page27.project.domain.MemberGrade;
import com.page27.project.repository.MemberRepository;
import com.page27.project.service.MemberService;
import com.page27.project.web.LoginForm;
import com.page27.project.web.MemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/adminUserlistPaging")
    public String pageList(@PageableDefault(size = 8) Pageable pageable, Model model) {

        Page<Member> memberList = memberService.findAllMembersByPaging(pageable);
        model.addAttribute("memberList", memberList);

        log.debug("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                memberList.getTotalElements(), memberList.getTotalPages(), memberList.getSize(),
                memberList.getNumber(), memberList.getNumberOfElements());
        return "admin/admin_userlist";
    }// 페이징 O 전체 멤버 조회 페이지

    @GetMapping("/members/join")
    public String makeJoinForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }//회원 가입 페이지 이동

    @PostMapping("/members/join")
    public String getJoinForm(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setLoginId(memberForm.getLoginId());
        member.setPassword(memberForm.getPassword());
        member.setEmail(memberForm.getEmail());
        member.setMemberGrade(MemberGrade.USER);
        member.setPhoneNumber(memberForm.getPhoneNumber());
        member.setBirthday(memberForm.getBirthday());

        memberService.join(member);
        return "redirect:/";
    }// 회원가입 데이터 저장

    @GetMapping("/members/login")
    public String makeLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/memberLogin";
    }//회원 가입 페이지 이동

}
