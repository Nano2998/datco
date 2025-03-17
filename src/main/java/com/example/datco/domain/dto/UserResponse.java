package com.example.datco.domain.dto;

import com.example.datco.domain.entity.User;
import com.example.datco.domain.enums.Role;

public record UserResponse(
        Long id,
        String email,
        String name,
        String password,
        Role role
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getRole()
        );
    }
}
