package com.page27.project.service;

import com.page27.project.domain.Member;
import com.page27.project.dto.MemberInfoDto;
import com.page27.project.dto.MyPageDto;
import com.page27.project.dto.ProfileDto;

public interface MemberService {

    Member findMemberById(Long id);
//    Pk를 이용한 회원 찾기

    Member findMemberByLoginId(String loginId);

    Long joinUser(MemberInfoDto memberInfoDto);
//    회원가입 메소드

    void updateProfile(String loginId, ProfileDto profileDto);
//    개인 정보 수정 메소드

    Long changePassword(Long id, String password);
//    비밀번호 변경 메소드

    MyPageDto showMySimpleInfo(String loginId);
//    내정보(마일리지, 등급, 이름) 보여주는 메소드

    boolean doubleCheckId(String registerId);
//    회원 중복 체크 메소드

    void deleteMemberByLoginId(String loginId);
//    회원 탈퇴 기능 메소드

    Long deleteById(Long id);
//    Pk를 이용한 회원 삭제 기능
    
    ProfileDto showProfileData(String loginId);
}
