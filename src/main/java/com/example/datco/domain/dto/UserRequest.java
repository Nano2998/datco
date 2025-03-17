package com.example.datco.domain.dto;


import com.example.datco.domain.entity.User;
import com.example.datco.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
public record UserRequest(
        String email,
        String name,
        String password,
        Role role
) {
    public User toEntity(String encode) {
        return User.builder()
                .name(name)
                .email(this.email)
                .password(encode)
                .role(role)
                .build();
    }

}
