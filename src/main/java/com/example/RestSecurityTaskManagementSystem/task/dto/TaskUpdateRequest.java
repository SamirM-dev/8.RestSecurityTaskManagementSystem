package com.example.RestSecurityTaskManagementSystem.task.dto;

import com.example.RestSecurityTaskManagementSystem.enums.TaskPriority;
import com.example.RestSecurityTaskManagementSystem.enums.TaskStatus;
import com.example.RestSecurityTaskManagementSystem.validation.ValidTaskPriority;
import com.example.RestSecurityTaskManagementSystem.validation.ValidTaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskUpdateRequest(
        @NotBlank(message = "The title must be entered") @Size(min = 3,max = 100,message = "Title's length must be between 3 and 100 symbols")
        String title,
        @NotBlank(message = "The description must be entered") @Size(min = 1,max = 500,message = "Description's length must be between 3 and 100 symbols")
        String description,
        @NotNull(message = "The Status must be entered") @ValidTaskStatus(message = "Enter status in valid format")
        TaskStatus status,
        @NotNull(message = "The priority must be entered") @ValidTaskPriority(message = "Enter priority in valid format")
        TaskPriority priority) {}
