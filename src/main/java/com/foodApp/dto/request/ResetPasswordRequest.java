package com.foodApp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ResetPasswordRequest {
    @NotBlank(message = "Code is required")
    private String otpCode;

    @Email(message = "Email invalid format")
    private String email;
}
