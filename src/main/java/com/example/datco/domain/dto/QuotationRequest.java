package com.example.datco.domain.dto;

import com.example.datco.domain.entity.Order;
import com.example.datco.domain.entity.Quotation;
import com.example.datco.domain.entity.User;
import com.example.datco.domain.enums.Status;

import java.time.LocalDateTime;

public record QuotationRequest(
        Long estimatedCost,
        LocalDateTime productionTime
) {
    public Quotation toEntity(User user,Order order) {
        return Quotation.builder()
                .user(user)
                .request(order)
                .status(Status.PENDING_REVIEW)
                .estimatedCost(estimatedCost)
                .productionTime(productionTime)
                .build();
    }
}
