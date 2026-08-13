package com.example.RestSecurityTaskManagementSystem.task.dto;

import com.example.RestSecurityTaskManagementSystem.enums.TaskPriority;
import com.example.RestSecurityTaskManagementSystem.enums.TaskStatus;

public record TaskUpdateRequest(String title, String  description, TaskStatus status, TaskPriority priority) {
}
