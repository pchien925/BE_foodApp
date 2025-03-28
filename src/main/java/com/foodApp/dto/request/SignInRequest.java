package com.foodApp.dto.request;

import com.foodApp.util.EnumValue;
import com.foodApp.util.Platform;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInRequest {
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "password must be not null")
    private String password;

    @EnumValue(name = "platform", enumClass = Platform.class)
    private String platform;

    private String deviceToken;
}
