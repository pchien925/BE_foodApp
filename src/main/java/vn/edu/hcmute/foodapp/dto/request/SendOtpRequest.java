package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SendOtpRequest {
    @Email(message = "Email is not valid")
    @NotBlank
    private String email;
}
