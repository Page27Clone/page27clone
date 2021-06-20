package com.page27.project.service;

import com.page27.project.domain.Member;
import com.page27.project.domain.Mileage;
import com.page27.project.dto.BasketMemberMileageDto;
import com.page27.project.repository.BasketRepository;
import com.page27.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final MemberRepository memberRepository;
    private final MileageService mileageService;

    public BasketMemberMileageDto findMemberMileage(String loginId){
        Optional<Member> byloginId = memberRepository.findByloginId(loginId);
        Member findMember = byloginId.get();
        BasketMemberMileageDto basketMemberMileageDto = new BasketMemberMileageDto();
        int totalMileage = mileageService.getTotalMileage(loginId);
        int totalUsedMileage = mileageService.getTotalUsedMileage(loginId);
        int restMileage = totalMileage - totalUsedMileage;

        basketMemberMileageDto.setName(findMember.getName());
        basketMemberMileageDto.setLoginId(loginId);
        basketMemberMileageDto.setTotalMileage(totalMileage);
        basketMemberMileageDto.setUsedMileage(totalUsedMileage);
        basketMemberMileageDto.setRestMileage(restMileage);

        basketMemberMileageDto.setMemberGrade("일반회원");
//        등급 회원 이 부분 나중에 고칠 예정

        return basketMemberMileageDto;
    }


}
