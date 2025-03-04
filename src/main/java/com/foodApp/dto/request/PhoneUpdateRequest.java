package com.foodApp.dto.request;

import com.foodApp.util.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PhoneUpdateRequest {
    @PhoneNumber(message = "phone invalid format")
    private String phone;
}
