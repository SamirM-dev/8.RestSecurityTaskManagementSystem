package com.example.RestSecurityTaskManagementSystem.task.dto;

import com.example.RestSecurityTaskManagementSystem.enums.TaskPriority;
import com.example.RestSecurityTaskManagementSystem.enums.TaskStatus;

public record TaskCreateRequest(String title, String description,TaskPriority priority) {
}
