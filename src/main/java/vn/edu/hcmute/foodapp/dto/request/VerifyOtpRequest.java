package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class VerifyOtpRequest {
    @Email(message = "Email is not valid")
    @NotBlank
    private String email;

    @NotBlank
    private String otp;
}
