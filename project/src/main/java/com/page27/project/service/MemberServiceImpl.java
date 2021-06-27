package com.page27.project.service;

import com.page27.project.config.Role;
import com.page27.project.domain.*;
import com.page27.project.dto.*;
import com.page27.project.exception.DuplicateException;
import com.page27.project.exception.LoginIdNotFoundException;
import com.page27.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.context.annotation.Profile;
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
public class MemberServiceImpl implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new LoginIdNotFoundException("해당하는 회원이 존재하지 않습니다")
        );
    }

    @Override
    public Member findMemberByLoginId(String loginId) {
        return memberRepository.findByloginId(loginId).orElseThrow(
                () -> new LoginIdNotFoundException("해당하는 회원이 존재하지 않습니다")
        );

    }

    @Transactional
    @Override
    public Long deleteById(Long id) {
        memberRepository.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public Long joinUser(MemberInfoDto memberInfoDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberInfoDto.setPassword(passwordEncoder.encode(memberInfoDto.getPassword()));

        return memberRepository.save(memberInfoDto.toEntity()).getId();
    }

    @Override
    public ProfileDto showProfileData(String loginId) {
        ProfileDto myProfileDto = new ProfileDto();

        Member findMember = memberRepository.findByloginId(loginId).orElseThrow(
                () -> new LoginIdNotFoundException("해당하는 회원을 찾을 수 없습니다")
        );

        String homePhoneNumber = findMember.getHomePhoneNumber();
        String[] homePhoneNumberArr = homePhoneNumber.split(",");
        String phoneNumber = findMember.getPhoneNumber();
        String[] phoneNumberArr = phoneNumber.split(",");
        String birthday = findMember.getBirthday();
        String[] birthdayArr = birthday.split(",");

        if (findMember.getMemberAddress() == null) {
            findMember.setMemberAddress(new MemberAddress("", "", ""));
        }

        myProfileDto.setName(findMember.getName());
        myProfileDto.setLoginId(findMember.getLoginId());
        myProfileDto.setStreet(findMember.getMemberAddress().getStreet());
        myProfileDto.setZipcode(findMember.getMemberAddress().getZipcode());
        myProfileDto.setCity(findMember.getMemberAddress().getCity());
        myProfileDto.setHomePhoneNumber(homePhoneNumberArr);
        myProfileDto.setPhoneNumber(phoneNumberArr);
        myProfileDto.setEmail(findMember.getEmail());
        myProfileDto.setBirthday(birthdayArr);

        return myProfileDto;
    }

    @Override
    public Page<Member> findAllMemberByOrderByCreatedAt(Pageable pageable) {
        return memberRepository.findAllByOrderByCreatedAt(pageable);
    }

    @Override
    public MemberPageDto findAllMemberByPaging(Pageable pageable) {
        MemberPageDto memberPageDto = new MemberPageDto();
        Page<MemberDto> memberBoards = memberRepository.searchAll(pageable);
        int homeStartPage = Math.max(1, memberBoards.getPageable().getPageNumber());
        int homeEndPage = Math.min(memberBoards.getTotalPages(), memberBoards.getPageable().getPageNumber() + 5);

        memberPageDto.setMemberBoards(memberBoards);
        memberPageDto.setHomeStartPage(homeStartPage);
        memberPageDto.setHomeEndPage(homeEndPage);

        return memberPageDto;
    }

    @Override
    public MemberPageDto findAllMemberByConditionByPaging(SearchMember searchMember, Pageable pageable) {
        MemberPageDto memberPageDto = new MemberPageDto();
        Page<MemberDto> memberBoards = memberRepository.searchByCondition(searchMember, pageable);

        int startPage = Math.max(1,memberBoards.getPageable().getPageNumber()-2);
        int endPage = Math.min(memberBoards.getTotalPages(),startPage + 4);

        memberPageDto.setMemberBoards(memberBoards);
        memberPageDto.setHomeStartPage(startPage);
        memberPageDto.setHomeEndPage(endPage);

        return memberPageDto;
    }

    @Override
    public int getVisitCount() {
        return memberRepository.visitCountResult();
    }

    @Transactional
    @Override
    public void updateProfile(String loginId, ProfileDto profileDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member findMember = memberRepository.findByloginId(loginId).orElseThrow(
                () -> new LoginIdNotFoundException("해당하는 회원을 찾을 수 없습니다")
        );

        String homePhoneNumberResult = profileDto.getHomePhoneNumber()[0] + "," + profileDto.getHomePhoneNumber()[1] + "," + profileDto.getHomePhoneNumber()[2];
        String phoneNumberResult = profileDto.getPhoneNumber()[0] + "," + profileDto.getPhoneNumber()[1] + "," + profileDto.getPhoneNumber()[2];
        MemberAddress memberAddress = new MemberAddress(profileDto.getCity(), profileDto.getStreet(), profileDto.getZipcode());

        findMember.setName(profileDto.getName());
        findMember.setLoginId(profileDto.getLoginId());
        findMember.setPassword(passwordEncoder.encode(profileDto.getPassword()));

        findMember.setHomePhoneNumber(homePhoneNumberResult);
        findMember.setPhoneNumber(phoneNumberResult);
        findMember.setEmail(profileDto.getEmail());
        findMember.setMemberAddress(memberAddress);
    }

    @Transactional
    @Override
    public Long changePassword(Long id, String password) {
        Member oneMember = findMemberById(id);
        oneMember.setPassword(password);
        return oneMember.getId();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Optional<Member> userEntityWrapper = memberRepository.findByloginId(loginId);

        Member userEntity = userEntityWrapper.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin@example.com").equals(loginId)) {
            authorities.add(new SimpleGrantedAuthority(MemberGrade.ADMIN.getValue()));
            userEntity.setMemberGrade(MemberGrade.ADMIN);
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberGrade.MEMBER.getValue()));
            userEntity.setMemberGrade(MemberGrade.MEMBER);
        }
        System.out.println("here authority check : " + authorities.size());
        System.out.println("here authority check : " + authorities.get(0).getAuthority());

        int visitCount = userEntity.getVisitCount();
        userEntity.setVisitCount(++visitCount);

        return new User(userEntity.getLoginId(), userEntity.getPassword(), authorities);
    }
//    상세정보를 조회하는 메소드이며 사용자의 계정정보와 권한을 갖는 UserDetails 인터페이스를 반환해야한다.
//    매개변수는 로그인 시 입력한 아이디이다. 엔티티의 PK를 뜻하는게 아니고 유저를 식별할 수 있는 어떤 값을 의미한다.
//    스프링 시큐리티에서는 username라는 이름으로 사용한다.
//    로그인을 하는 form에서 name = "username"으로 요청해야 한다.
//    authorities.add~ 롤을 부여하는 코드이다. 롤 부여 방식에는 여러가지가 있지만, 회원가입할 때 Role을 정할 수 있도록 Role Entity를 만들어서 매핑하는 것이 좋다.
//    return은 스프링 시큐리티에서 제공하는 UserDetails를 구현한 User를 반환한다.
//    생성자의 각 매개변수는 순서대로 아이디, 비밀번호, 권한리스트이다.

    @Override
    public MyPageDto showMySimpleInfo(String loginId) {
        int totalMileage = 0;
        int usedMileage = 0;
        MyPageDto myPageDto = new MyPageDto();

        Member findMember = memberRepository.findByloginId(loginId).orElseThrow(
                () -> new LoginIdNotFoundException("해당하는 회원이 존재하지 않습니다")
        );

        for (int i = 0; i < findMember.getMileageList().size(); i++) {
            totalMileage += findMember.getMileageList().get(i).getMileagePrice();
        }
        for (int j = 0; j < findMember.getOrderList().size(); j++) {
            usedMileage += findMember.getOrderList().get(j).getUsedMileagePrice();
        }

        myPageDto.setName(findMember.getName());
        myPageDto.setGrade(findMember.getMemberGrade());
        myPageDto.setMileage(totalMileage - usedMileage);

        return myPageDto;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean doubleCheckId(String registerId) {
        Optional<Member> findMember = memberRepository.findByloginId(registerId);
        return findMember.isPresent();
    }

    @Transactional
    @Override
    public void deleteMemberByLoginId(String loginId) {
        memberRepository.deleteByLoginId(loginId);
    }
    //    회원탈퇴 기능


}
