package com.example.datco.domain.filters;

import com.example.datco.common.JwtUtils;
import com.example.datco.domain.dto.CustomUserDetails;
import com.example.datco.domain.entity.User;
import com.example.datco.domain.enums.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null){
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);

        if(!jwtUtil.getType(token).equals("access")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("invalid token");
        }
        if(jwtUtil.isExpired(token)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("token is expired");
            return;
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(
                new User().builder()
                        .id(jwtUtil.getId(token))
                        .role(Role.CLIENT)
                        .build()
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
