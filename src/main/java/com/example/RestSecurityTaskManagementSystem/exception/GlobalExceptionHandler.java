package com.example.RestSecurityTaskManagementSystem.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                400,"BAD REQUEST",e.getMessage(),request.getRequestURI()
        );

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                404,"NOT FOUND",e.getMessage(),request.getRequestURI()
        );

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExist(ResourceAlreadyExistException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                409,"Conflict",e.getMessage(),request.getRequestURI()
        );

        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e,HttpServletRequest request){
        List<ErrorResponse.ErrorField> errorFields = e.getBindingResult().getFieldErrors()
                .stream().map(field->new ErrorResponse.ErrorField(field.getField(),field.getDefaultMessage())).toList();
        ErrorResponse response=new ErrorResponse(
                400,"BAD REQUEST",e.getMessage(),request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                400,"BAD REQUEST", e.getMessage(), request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),"Server has some problems...",request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
    }
}
