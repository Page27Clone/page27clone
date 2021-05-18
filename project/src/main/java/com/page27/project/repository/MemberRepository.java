package com.page27.project.repository;

import com.page27.project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long>,MemberRepositoryCustom{
    List<Member> findByName(String name);
}
