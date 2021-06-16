package com.page27.project.repository;

import com.page27.project.domain.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
}
