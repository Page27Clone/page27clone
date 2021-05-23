package com.page27.project.repository;

import com.page27.project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long>,MemberRepositoryCustom{
    List<Member> findByName(String name);

    //Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByloginId(String loginId);
}
