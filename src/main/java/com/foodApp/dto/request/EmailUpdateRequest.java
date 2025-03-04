package com.foodApp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailUpdateRequest {
    @Email(message = "email invalid")
    private String newEmail;
}
