package com.foodApp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    @Email(message = "email invalid format")
    private String email;

    @NotBlank(message = "password must be not null")
    private String password;

    @NotBlank(message = "confirmPassword must be not null")
    private String confirmPassword;


}
