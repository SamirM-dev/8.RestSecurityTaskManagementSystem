package com.example.RestSecurityTaskManagementSystem.task.dto;

import com.example.RestSecurityTaskManagementSystem.enums.TaskPriority;
import com.example.RestSecurityTaskManagementSystem.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id, String title, String description,
        TaskStatus status, TaskPriority priority,
        LocalDateTime createdAt,LocalDateTime updatedAt,Long userId
)
{
}
