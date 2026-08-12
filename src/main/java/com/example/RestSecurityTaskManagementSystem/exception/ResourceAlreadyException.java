package com.example.RestSecurityTaskManagementSystem.exception;

public class ResourceAlreadyException extends RuntimeException {
    public ResourceAlreadyException(String message) {
        super(message);
    }
}
