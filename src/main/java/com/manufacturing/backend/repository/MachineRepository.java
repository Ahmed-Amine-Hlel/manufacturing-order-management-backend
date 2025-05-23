package com.manufacturing.backend.repository;

import com.manufacturing.backend.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {}
