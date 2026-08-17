package com.example.RestSecurityTaskManagementSystem.auth;

import com.example.RestSecurityTaskManagementSystem.auth.dto.LoginRequest;
import com.example.RestSecurityTaskManagementSystem.auth.dto.RefreshTokenRequest;
import com.example.RestSecurityTaskManagementSystem.auth.dto.RegisterRequest;
import com.example.RestSecurityTaskManagementSystem.auth.dto.TokenResponse;
import com.example.RestSecurityTaskManagementSystem.auth.oauth2.ExchangeRequest;
import com.example.RestSecurityTaskManagementSystem.details.UserPrincipal;
import com.example.RestSecurityTaskManagementSystem.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@Valid@RequestBody RegisterRequest request){
        return ResponseEntity.status(201).body(authService.register(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponse> login(@Valid@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping("/auth/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> logout(@Valid@RequestBody RefreshTokenRequest request){
        authService.logout(request);
        return ResponseEntity.ok("You have successfully logged out");
    }

    @PostMapping("/auth/exchange")
    public ResponseEntity<TokenResponse> exchange(@Valid@RequestBody ExchangeRequest request){
        return ResponseEntity.ok(authService.exchange(request));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(authService.me(principal));
    }
}
