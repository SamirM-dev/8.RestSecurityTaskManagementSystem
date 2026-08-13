package com.example.RestSecurityTaskManagementSystem.task.dto;

import com.example.RestSecurityTaskManagementSystem.enums.TaskStatus;
import com.example.RestSecurityTaskManagementSystem.validation.ValidTaskPriority;
import jakarta.validation.constraints.NotNull;

public record TaskStatusUpdateRequest(
        @NotNull(message = "The Status must be entered") @ValidTaskPriority(message = "Enter status in valid format")
        TaskStatus status) {}
