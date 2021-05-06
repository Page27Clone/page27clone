package com.page27.project.repository;

import com.page27.project.domain.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member,Long> {
    @Query("select distinct m from Member m left join fetch m.mileage")
    List<Member> findAllWithMileage();
}
