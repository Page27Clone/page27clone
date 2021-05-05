package com.page27.project.controller;

import com.page27.project.domain.Member;
import com.page27.project.dto.MemberDto;
import com.page27.project.repository.MemberRepository;
import com.page27.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("adminUserlist")
    public String adminUserlist(Model model, Pageable pageable) {
        List<Member> memberList = memberService.findMembers();
        model.addAttribute("userList",memberList);
        return "admin_userlist";
    }

//    @GetMapping("adminUserlist")
//    public Page<MemberDto> list(@PageableDefault(size=5) Pageable pageable){
//        Page<Member> page = memberRepository.findAll(pageable);
//        Page<MemberDto> map = page.map(member -> new MemberDto(member));
//        return map;
//    }
}
