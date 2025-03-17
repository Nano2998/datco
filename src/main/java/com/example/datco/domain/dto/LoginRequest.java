package com.example.datco.domain.dto;

public record LoginRequest(
        String email,
        String password
) {
}
