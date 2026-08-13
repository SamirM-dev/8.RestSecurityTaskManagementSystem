package com.example.RestSecurityTaskManagementSystem.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentCreateRequest(
        @NotBlank(message = "The text must be entered") @Size(min = 1,max = 500,message = "Comment's text length must be between 1 and 500 symbols")
        String text,
        @NotBlank(message = "The author's name must be entered") @Size(min = 3,max = 50,message = "Author's name length must be between 3 and 50 symbols")
        String author) {
}
