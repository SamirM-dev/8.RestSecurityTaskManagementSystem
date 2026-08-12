package com.example.RestSecurityTaskManagementSystem.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {
    private long status;
    private String error;
    private String message;
    private String path;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private List<ErrorResponse> errorResponses = new ArrayList<>();

    public ErrorResponse(long status,String error,String message,String path){
        this.status=status;
        this.error=error;
        this.message=message;
        this.path=path;
        this.timestamp=LocalDateTime.now();
    }
    public void setErrorResponses(List<ErrorResponse> errorResponses){
        this.errorResponses=errorResponses;
    }

    @AllArgsConstructor
    @Getter
    public static class ErrorField{
        private String field;
        private String message;
    }
}
