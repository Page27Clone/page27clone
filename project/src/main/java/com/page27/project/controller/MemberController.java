package com.page27.project.controller;

import com.page27.project.domain.Member;
import com.page27.project.dto.MemberDto;
import com.page27.project.repository.MemberRepository;
import com.page27.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

//    @GetMapping("adminUserlist")
//    public String adminUserlist(Model model, @PageableDefault(size = 5) Pageable pageable) {
//        Page<Member> memberList = memberService.findMemberList(pageable);
//        model.addAttribute("userList",memberList);
//        return "admin_userlist";
//    }

    @GetMapping("adminUserlist")
    public String list(Model model){
        List<Member> memberList = memberService.findMembers();
        model.addAttribute("memberList",memberList);
        return "admin_userlist";
    }
    @GetMapping("adminUserlistTemp")
    public String pageList(@PageableDefault(size = 5) Pageable pageable,Model model){
        Page<Member> memberList = memberService.getMemberList(pageable);
        model.addAttribute("memberList",memberList);

        log.debug("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                memberList.getTotalElements(), memberList.getTotalPages(), memberList.getSize(),
                memberList.getNumber(), memberList.getNumberOfElements());
        return "admin_userlist";
    }
    @GetMapping("adminUserlistWith")
    public String withList(Model model){
        List<Member> memberList = memberRepository.findAllWithMileage();
        model.addAttribute("memberList",memberList);
        return "admin_userlist";
    }

//    @PostConstruct
//    public void init(){
//        for(int i = 0;i<25;i++){
//            memberRepository.save(new Member("user"+ i,i));
//        }
//    }
}
