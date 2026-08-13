package com.example.RestSecurityTaskManagementSystem.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TaskPriorityValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidTaskPriority {
    String message() default "";
    Class<?>[] groups() default {};
    Class<?extends Payload>[] payload() default {};
}
