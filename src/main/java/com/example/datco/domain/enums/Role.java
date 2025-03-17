package com.example.datco.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role {
    CLIENT("CLIENT",new SimpleGrantedAuthority("CLIENT")),
    SUPPLY("SUPPLY",new SimpleGrantedAuthority("SUPPLY")),
    ADMIN("ADMIN",new SimpleGrantedAuthority("ADMIN"));

    private final String role;
    private final SimpleGrantedAuthority authority;

}
