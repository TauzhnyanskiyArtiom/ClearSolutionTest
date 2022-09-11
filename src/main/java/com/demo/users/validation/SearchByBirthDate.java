package com.demo.users.validation;

import com.demo.users.validation.impl.BirthDateInfoValidator;
import com.demo.users.validation.impl.SearchByBirthDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SearchByBirthDateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchByBirthDate {
    String message() default "From is less than To";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
