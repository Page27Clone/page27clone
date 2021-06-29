package com.page27.project.repository;

import com.page27.project.domain.Member;
import com.page27.project.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByloginId(String loginId);

    Page<Member> findAllByOrderByCreatedAt(Pageable pageable);

    @Query("select sum(m.visitCount) from Member m")
    int visitCountResult();

    void deleteByLoginId(String loginId);
}
