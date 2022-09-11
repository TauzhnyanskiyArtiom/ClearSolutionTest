package com.demo.users.api.dto;

import com.demo.users.validation.BirthDateInfo;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Value
public class UserEditDto {

    @Email
    String email;

    String firstname;

    String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birth date must be earlier than current date")
    @BirthDateInfo
    LocalDate birthDate;

    String address;

    String phoneNumber;

}
