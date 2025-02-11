package com.foodApp.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.foodApp.util.EnumValue;
import com.foodApp.util.Gender;
import com.foodApp.util.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
public class RegisterRequest {
    @NotBlank(message = "username must be not null")
    private String username;

    @NotBlank(message = "password must be not null")
    private String password;

    @Email(message = "email invalid format")
    private String email;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotBlank(message = "lastname must be not null")
    private String lastName;

    @NotBlank(message = "firstname must be not null")
    private String firstName;

    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dob;

    @NotNull(message = "gender must be not null")
    @EnumValue(name = "gender", enumClass = Gender.class)
    private String gender;
}
