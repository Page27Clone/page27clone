package com.page27.project.service;

import com.page27.project.config.Role;
import com.page27.project.domain.Member;
import com.page27.project.dto.MemberInfoDto;
import com.page27.project.exception.DuplicateException;
import com.page27.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

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

    @Transactional
    public Long deleteById(Long id){
        memberRepository.deleteById(id);
        return id;
    }

    //여기서부터 수정
    @Transactional
    public Long joinUser(MemberInfoDto memberInfoDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberInfoDto.setPassword(passwordEncoder.encode(memberInfoDto.getPassword()));


        //System.out.println("here is joinUser method " + memberInfoDto.toEntity().getId());
        return memberRepository.save(memberInfoDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        System.out.println("Login Id = " + loginId);
        Optional<Member> userEntityWrapper = memberRepository.findByloginId(loginId);
        System.out.println("why");
        System.out.println(memberRepository.findByloginId("here check : " + userEntityWrapper .get().getLoginId()));
        Member userEntity = userEntityWrapper.get();
        System.out.println("userEntity ID = " + userEntity.getLoginId());
        System.out.println("userEntity Password = " + userEntity.getPassword());
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin@example.com").equals(loginId)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        return new User(userEntity.getLoginId(), userEntity.getPassword(), authorities);
    }

}
