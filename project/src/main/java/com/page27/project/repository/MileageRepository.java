package com.page27.project.repository;

import com.page27.project.domain.Mileage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MileageRepository extends JpaRepository<Mileage,Long> ,MileageRepositoryCustom{

}
