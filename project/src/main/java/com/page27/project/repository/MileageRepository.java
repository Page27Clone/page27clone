package com.page27.project.repository;

import com.page27.project.domain.Mileage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface MileageRepository extends JpaRepository<Mileage,Long> {

}
