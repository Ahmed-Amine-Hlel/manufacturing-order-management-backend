package com.manufacturing.backend.repository;

import com.manufacturing.backend.model.ManufacturingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturingOrderRepository extends JpaRepository<ManufacturingOrder, Long> {}
