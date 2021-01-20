package com.nhat.demo.repository;

import com.nhat.demo.entity.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRepository extends JpaRepository<Charge, Integer> {
}
