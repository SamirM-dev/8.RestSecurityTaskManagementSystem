package com.example.RestSecurityTaskManagementSystem.task.dto;

import com.example.RestSecurityTaskManagementSystem.enums.TaskStatus;

public record TaskStatusUpdateRequest(TaskStatus status) {
}
