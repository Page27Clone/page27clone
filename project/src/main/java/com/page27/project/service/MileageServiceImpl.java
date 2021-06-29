package com.page27.project.service;

import com.page27.project.domain.Member;
import com.page27.project.domain.Mileage;
import com.page27.project.dto.MileagePageDto;
import com.page27.project.exception.LoginIdNotFoundException;
import com.page27.project.repository.MemberRepository;
import com.page27.project.repository.MileageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MileageServiceImpl implements MileageService {

    private final MemberRepository memberRepository;
    private final MileageRepository mileageRepository;

    @Override
    public int getTotalMileage(String loginId) {
        Member member = memberRepository.findByloginId(loginId).get();

        int totalMileage = 0;

        for (int i = 0; i < member.getMileageList().size(); i++) {
            totalMileage += member.getMileageList().get(i).getMileagePrice();
            System.out.println(totalMileage);
        }
        return totalMileage;
    }

    @Override
    public int getTotalUsedMileage(String loginId) {
        Member member = memberRepository.findByloginId(loginId).get();
        int totalUsedMileage = 0;

        for (int i = 0; i < member.getOrderList().size(); i++) {
            totalUsedMileage += member.getOrderList().get(i).getUsedMileagePrice();
        }
        return totalUsedMileage;
    }

    @Override
    public int availableMileage(int totalMileage, int totalUsedMileage) {
        return totalMileage - totalUsedMileage;
    }

    @Override
    public List<Mileage> findAllMileageInfo(String loginId) {
        Member member = memberRepository.findByloginId(loginId).get();
        System.out.println("check : " + member.getMileageList().size());
        List<Mileage> mileageList = member.getMileageList();
        return mileageList;
    }

    @Override
    public Long joinUserMileage(Long id) {
        Member member = memberRepository.findById(id).get();
        Mileage mileage = new Mileage();
        mileage.setMileagePrice(2000);
        mileage.setMileageContent("회원가입 적립금");
        mileage.setMember(member);

        mileageRepository.save(mileage);

        return mileage.getId();
    }

    @Override
    public MileagePageDto getMileagePagingDto(String loginId, Pageable pageable) {
        MileagePageDto mileagePageDto = new MileagePageDto();

        Member findMember = memberRepository.findByloginId(loginId).orElseThrow(
                () -> new LoginIdNotFoundException("해당하는 회원이 존재하지 않습니다")
        );
        Page<Mileage> mileageBoards = mileageRepository.findAllByMember(findMember, pageable);
        int homeStartPage = Math.max(1, mileageBoards.getPageable().getPageNumber() - 4);
        int homeEndPage = Math.min(mileageBoards.getTotalPages(), mileageBoards.getPageable().getPageNumber() + 4);

        mileagePageDto.setMileageBoards(mileageBoards);
        mileagePageDto.setHomeStartPage(homeStartPage);
        mileagePageDto.setHomeEndPage(homeEndPage);

        return mileagePageDto;
    }
}
