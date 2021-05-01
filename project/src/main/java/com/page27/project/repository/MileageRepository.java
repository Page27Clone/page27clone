package com.page27.project.repository;

import com.page27.project.domain.Mileage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MileageRepository {
    private final EntityManager em;

    public void saveMileage(Mileage mileage){
        em.persist(mileage);
    }
}
