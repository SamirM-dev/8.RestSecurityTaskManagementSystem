package com.example.RestSecurityTaskManagementSystem.auth.oauth2;

import jakarta.validation.constraints.NotBlank;

public record ExchangeRequest(@NotBlank String code) {
}
