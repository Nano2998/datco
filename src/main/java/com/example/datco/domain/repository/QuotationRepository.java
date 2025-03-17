package com.example.datco.domain.repository;

import com.example.datco.domain.entity.Quotation;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {

    List<Quotation> findByRequestId(Long orderId);
}
