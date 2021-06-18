package com.page27.project.controller.admin;

import com.page27.project.domain.Member;
import com.page27.project.domain.OrderItem;
import com.page27.project.domain.SearchMember;
import com.page27.project.dto.MemberDto;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.OrderItemRepository;
import com.page27.project.repository.OrderRepository;
import com.page27.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @GetMapping("/userList")
    public String pageList(Model model, @PageableDefault(size=8) Pageable pageable, SearchMember searchMember) {
        if(searchMember.getSearchKeyword() == null){
            Page<Member> memberBoards = memberRepository.findAll(pageable);
            int homeStartPage = Math.max(1,memberBoards.getPageable().getPageNumber() - 4);
            int homeEndPage = Math.min(memberBoards.getTotalPages(),memberBoards.getPageable().getPageNumber() + 4);
            model.addAttribute("startPage",homeStartPage);
            model.addAttribute("endPage",homeEndPage);
            model.addAttribute("memberList",memberBoards);

            return "admin/admin_userlist";
        }
        Page<MemberDto> memberBoards = memberRepository.searchByCondition(searchMember, pageable);

        int startPage = Math.max(1,memberBoards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(memberBoards.getTotalPages(),memberBoards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("memberList",memberBoards);
        model.addAttribute("searchCondition",searchMember.getSearchCondition());
        model.addAttribute("searchKeyword",searchMember.getSearchKeyword());

        return "admin/admin_userlist";
    }// 페이징 O 전체 멤버 조회 페이지

    @GetMapping("/userList/user/{id}")
    public String pageUser(@PathVariable Long id, Model model){
        model.addAttribute("Member",memberService.findOneMember(id));

        return "admin/admin_user";
    }

    @DeleteMapping("/userList/{id}")
    public @ResponseBody String test(@PathVariable Long id){
        System.out.println("here delete part");
        memberService.deleteById(id);
//        orderRepository.find
        return "Ajax 통신완료";
    }

    @DeleteMapping("/userList")
    public @ResponseBody String deleteChecked(@RequestParam(value="idList", required = false) List<Long> idList) {
        int size = idList.size();

        for(int i = 0;i < size;i++){
            memberService.deleteById(idList.get(i));
        }
        return "선택 삭제 완료";
    }


}
