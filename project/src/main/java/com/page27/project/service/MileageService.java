package com.page27.project.service;

import com.page27.project.domain.Member;
import com.page27.project.domain.Mileage;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.MileageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MileageService {

    private final MemberRepository memberRepository;
    private final MileageRepository mileageRepository;

    public int getTotalMileage(String loginId){
        Member member = memberRepository.findByloginId(loginId).get();

        int totalMileage = 0;

        for(int i = 0;i< member.getMileageList().size() ; i++){
            totalMileage += member.getMileageList().get(i).getMileagePrice();
            System.out.println(totalMileage);
        }
        return totalMileage;
    }
}
