package com.example.datco.domain.dto;

import com.example.datco.domain.entity.Order;
import com.example.datco.domain.enums.Status;

public record OrderResponse(
        Long id,
        String title,
        String description,
        String designFilePath,
        Status status
) {
    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTitle(),
                order.getDescription(),
                order.getDesignFilePath(),
                order.getStatus()
        );
    }
}
