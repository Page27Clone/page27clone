package com.page27.project.repository;

import com.page27.project.domain.Search;
import com.page27.project.dto.MemberDto;
import com.page27.project.dto.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    //Page<MemberDto> searchAll(Pageable pageable);
    Page<MemberDto> searchByCondition(Search search, Pageable pageable);
}
