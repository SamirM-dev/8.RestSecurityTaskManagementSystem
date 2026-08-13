package com.example.RestSecurityTaskManagementSystem.validation;

import com.example.RestSecurityTaskManagementSystem.enums.TaskPriority;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class TaskPriorityValidator implements ConstraintValidator<ValidTaskPriority,TaskPriority> {

    private static final List<TaskPriority> ALLOWED_PRIORITY = List.of(TaskPriority.LOW,TaskPriority.MEDIUM,TaskPriority.HIGH);

    @Override
    public void initialize(ValidTaskPriority constraintAnnotation) {
    }

    @Override
    public boolean isValid(TaskPriority value, ConstraintValidatorContext context) {
        if (value==null){
            return true;
        }
        return ALLOWED_PRIORITY.contains(value);
    }
}
