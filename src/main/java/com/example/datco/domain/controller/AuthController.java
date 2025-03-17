package com.example.datco.domain.controller;

import com.example.datco.domain.dto.UserRequest;
import com.example.datco.domain.dto.UserResponse;
import com.example.datco.domain.dto.LoginRequest;
import com.example.datco.domain.dto.TokenResponse;
import com.example.datco.domain.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Auth controller api", description = "회원가입 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입")
    @PostMapping("/auth/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authService.signUp(userRequest));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}