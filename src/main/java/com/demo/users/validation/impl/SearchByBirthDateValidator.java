package com.demo.users.validation.impl;

import com.demo.users.api.dto.SearchUsersDto;
import com.demo.users.validation.SearchByBirthDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SearchByBirthDateValidator implements ConstraintValidator<SearchByBirthDate, SearchUsersDto> {
    @Override
    public boolean isValid(SearchUsersDto value, ConstraintValidatorContext context) {
        return value.getFrom().isBefore(value.getTo());
    }
}
