package com.example.RestSecurityTaskManagementSystem.comment.dto;

import java.time.LocalDateTime;

public record CommentResponse(Long id, String text, String author, LocalDateTime createdAt) {
}
