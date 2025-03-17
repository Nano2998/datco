package com.example.datco.common;

import com.example.datco.domain.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("No authentication information found");
        }
        return ((CustomUserDetails) authentication.getPrincipal()).getUser().getId();
    }
}
