package com.page27.project.repository;

import com.page27.project.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket,Long> {
    List<Basket> findAllByMemberId(Long id);

}
