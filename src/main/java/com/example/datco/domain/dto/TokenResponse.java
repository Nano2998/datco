package com.example.datco.domain.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}