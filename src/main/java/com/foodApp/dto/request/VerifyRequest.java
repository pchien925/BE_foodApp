package com.foodApp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class VerifyRequest {
    @Email(message = "email invalid format")
    private String email;

    @NotBlank(message = "code must be not null")
    private String code;
}
