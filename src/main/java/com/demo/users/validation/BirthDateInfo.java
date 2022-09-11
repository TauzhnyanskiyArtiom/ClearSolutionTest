package com.demo.users.validation;

import com.demo.users.validation.impl.BirthDateInfoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BirthDateInfoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDateInfo {
    String message() default "User must be older";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
