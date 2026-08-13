package com.example.RestSecurityTaskManagementSystem.validation;

import com.example.RestSecurityTaskManagementSystem.enums.TaskStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class TaskStatusValidator implements ConstraintValidator<ValidTaskStatus, TaskStatus> {

    private final static List<TaskStatus> ALLOWED_STATUS = List.of(TaskStatus.NEW,TaskStatus.IN_PROGRESS,TaskStatus.DONE);

    @Override
    public void initialize(ValidTaskStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(TaskStatus value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        return ALLOWED_STATUS.contains(value);
    }
}
