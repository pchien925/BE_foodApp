package com.foodApp.dto.request;

import com.foodApp.util.EnumValue;
import com.foodApp.util.Platform;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInRequest {
    @NotBlank(message = "username must be not null")
    private String username;

    @NotBlank(message = "password must be not null")
    private String password;

    @EnumValue(name = "platform", enumClass = Platform.class)
    private String platform;

    private String deviceToken;
}
