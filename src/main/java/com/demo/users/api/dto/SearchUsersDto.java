package com.demo.users.api.dto;

import com.demo.users.validation.SearchByBirthDate;
import lombok.Value;

import javax.validation.constraints.Past;
import java.time.LocalDate;

@Value
@SearchByBirthDate
public class SearchUsersDto {

    @Past
    LocalDate from;

    @Past
    LocalDate to;
}
