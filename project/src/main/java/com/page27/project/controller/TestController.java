package com.page27.project.controller;

import com.page27.project.domain.Member;
import com.page27.project.domain.Search;
import com.page27.project.dto.MemberSearchCondition;
import com.page27.project.repository.MemberRepository;
import com.page27.project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
public class TestController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping("/list")
    public String list(Model model, Pageable pageable){
        //Page<Member> boards = memberRepository.findAll(PageRequest.of(0,10));
        Page<Member> boards = memberRepository.findAll(pageable);
        int startPage = Math.max(0,boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);

        return "admin/admin_userlist";
    }


}
