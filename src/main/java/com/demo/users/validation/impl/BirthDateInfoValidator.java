package com.demo.users.validation.impl;

import com.demo.users.validation.BirthDateInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
public class BirthDateInfoValidator implements ConstraintValidator<BirthDateInfo, LocalDate> {

    @Value("${users.create.years}")
    private int yearsOldMore;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if(value == null)
            return true;

        int yearsOldUser = LocalDate.now().getYear() - value.getYear();
        return yearsOldUser >= yearsOldMore;
    }
}
