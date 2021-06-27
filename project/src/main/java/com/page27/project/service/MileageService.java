package com.page27.project.service;

import com.page27.project.domain.Mileage;
import com.page27.project.dto.MileagePageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MileageService {

    int getTotalMileage(String loginId);
//    전체 마일리지 조회하는 메소드

    int getTotalUsedMileage(String loginId);
//    사용한 마일리지 조회하는 메소드

    int availableMileage(int totalMileage, int totalUsedMileage);
//    사용가능한 마일리지 조회하는 메소드

    List<Mileage> findAllMileageInfo(String loginId);
//    해당하는 회원의 모든 마일리지 정보 조회하는 메소드

    Long joinUserMileage(Long id);
//    회원가입하게될 경우 마일리지 주는 메소드

    MileagePageDto getMileagePagingDto(String loginId, Pageable pageable);
//    마일리지 조회 및 페이징 하는 메소드
}
