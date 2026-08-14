package com.example.RestSecurityTaskManagementSystem.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email must be entered")@Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "Password must be entered")
        String password
) {
}
