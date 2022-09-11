package com.demo.users.api.dto;

import com.demo.users.validation.BirthDateInfo;
import com.demo.users.validation.group.CreateAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class UserCreateReplaceDto {

    @Email(message = "Must be in the format email address")
    @NotNull(message = "Email is required")
    private String email;

    @NotBlank(message = "Firstname is required")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    private String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be earlier than current date")
    @BirthDateInfo(groups = CreateAction.class)
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

}
