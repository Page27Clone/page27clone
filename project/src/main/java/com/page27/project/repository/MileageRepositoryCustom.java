package com.page27.project.repository;

import com.page27.project.domain.Mileage;
import com.page27.project.dto.MemberDto;
import com.page27.project.dto.MileageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MileageRepositoryCustom {

    int searchAllMileage();
}
