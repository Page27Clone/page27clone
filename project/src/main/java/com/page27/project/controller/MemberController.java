package com.page27.project.controller;

import com.page27.project.domain.Member;
import com.page27.project.domain.Search;
import com.page27.project.dto.MemberDto;
import com.page27.project.repository.MemberRepository;
import com.page27.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/userlist")
    public String pageList(Model model, @PageableDefault(size=8) Pageable pageable, Search search) {
        if(search.getSearchKeyword() == null){
            Page<Member> memberBoards = memberRepository.findAll(pageable);
            int homeStartPage = Math.max(1,memberBoards.getPageable().getPageNumber() - 4);
            int homeEndPage = Math.min(memberBoards.getTotalPages(),memberBoards.getPageable().getPageNumber() + 4);
            model.addAttribute("startPage",homeStartPage);
            model.addAttribute("endPage",homeEndPage);
            model.addAttribute("memberList",memberBoards);

            return "admin/admin_userlist";
        }
        Page<MemberDto> memberBoards = memberRepository.searchByCondition(search, pageable);

        int startPage = Math.max(1,memberBoards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(memberBoards.getTotalPages(),memberBoards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("memberList",memberBoards);

        return "admin/admin_userlist";
    }// 페이징 O 전체 멤버 조회 페이지

//    @GetMapping("/userlist/{id}")
//    public String test(@PathVariable Long id){
//         memberService.findOneMember(id);
//         return "lo";
//    }

    @DeleteMapping("/userlist/{id}")
    public @ResponseBody String adminDelete(@PathVariable Long id){
        System.out.println("here delete part");
        memberService.deleteById(id);
        return "Ajax 통신완료";
    }


}
