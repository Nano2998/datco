package com.example.datco.domain.service;

import com.example.datco.common.JwtUtils;
import com.example.datco.domain.dto.LoginRequest;
import com.example.datco.domain.dto.TokenResponse;
import com.example.datco.domain.dto.UserRequest;
import com.example.datco.domain.dto.UserResponse;
import com.example.datco.domain.entity.User;
import com.example.datco.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    public UserResponse signUp(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.email()) != null) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encode = passwordEncoder.encode(userRequest.password());
        User user = userRepository.save(userRequest.toEntity(encode));
        return UserResponse.from(user);
    }


    public TokenResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email());

        if (user == null || !passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new RuntimeException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        String accessToken = jwtUtils.makeJwtToken(user.getId());
        String refreshToken = jwtUtils.makeRefreshToken(user.getId());

        return new TokenResponse(accessToken, refreshToken);
    }
}