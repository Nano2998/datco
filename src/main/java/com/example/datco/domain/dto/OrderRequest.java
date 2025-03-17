package com.example.datco.domain.dto;

import com.example.datco.domain.entity.Order;
import com.example.datco.domain.entity.User;
import com.example.datco.domain.enums.Status;

public record OrderRequest(
        String title,
        String description,
        Status status
) {
    public Order toEntity(User user, String designFilePath) {
        return Order.builder()
                .user(user)
                .title(title)
                .status(status)
                .description(description)
                .designFilePath(designFilePath)
                .build();
    }
}
