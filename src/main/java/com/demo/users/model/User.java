package com.demo.users.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private String email;

    private String firstname;

    private String lastname;

    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

}