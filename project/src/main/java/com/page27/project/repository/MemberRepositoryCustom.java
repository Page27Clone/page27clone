package com.page27.project.repository;

import com.page27.project.domain.SearchMember;
import com.page27.project.dto.MemberDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


public interface MemberRepositoryCustom {
    Page<MemberDto> searchAll(Pageable pageable);

    Page<MemberDto> searchByCondition(SearchMember search, Pageable pageable);


}
