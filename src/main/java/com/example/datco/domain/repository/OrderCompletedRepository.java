package com.example.datco.domain.repository;

import com.example.datco.domain.entity.OrderCompleted;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCompletedRepository extends JpaRepository<OrderCompleted, Long> {

}
