package com.page27.project.service;

import com.page27.project.domain.Member;
import com.page27.project.exception.DuplicateException;
import com.page27.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private void validateMember(Member member) {
        List<Member> findMembers = memberRepository.findAll();
        if (!findMembers.isEmpty()) {
            throw new DuplicateException("이미 존재하는 회원입니다.");
        }
    }// 회원 중복 확인 메소드

    @Transactional
    public Long join(Member member) {
        validateMember(member);
        memberRepository.save(member);

        return member.getId();
    }// 회원 가입 메소드

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }// 회원 전체 조회

    public Page<Member> findAllMembersByPaging(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 8);

        return memberRepository.findAll(pageable);
    }// 회원 전체 조회 (페이지 이용)

    public Member findOneMember(Long id){
        return memberRepository.findById(id).get();
    }
}
