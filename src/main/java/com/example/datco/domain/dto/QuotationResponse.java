package com.example.datco.domain.dto;

import com.example.datco.domain.entity.Quotation;
import com.example.datco.domain.enums.Status;

import java.time.LocalDateTime;

public record QuotationResponse(
        Long id,
        String userName,
        String orderName,
        Long estimatedCost,
        Status status,
        LocalDateTime productionTime
) {
    public static QuotationResponse from(Quotation quotation) {
        return new QuotationResponse(
                quotation.getId(),
                quotation.getName(),
                quotation.getRequest().getTitle(),
                quotation.getEstimatedCost(),
                quotation.getStatus(),
                quotation.getProductionTime()
        );
    }
}
