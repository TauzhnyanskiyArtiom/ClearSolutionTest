package com.demo.users.api.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserReadDto {

    private String id;

    private String email;

    private String firstname;

    private String lastname;

    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

}
