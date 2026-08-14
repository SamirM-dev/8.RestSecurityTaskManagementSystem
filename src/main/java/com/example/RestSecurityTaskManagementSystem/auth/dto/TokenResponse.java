package com.example.RestSecurityTaskManagementSystem.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String type
) {
}
