package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import vn.edu.hcmute.foodapp.util.EnumValue;
import vn.edu.hcmute.foodapp.util.enumeration.EPlatform;

@Getter
public class SignInRequest {
    @NotBlank
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "password must be not null")
    private String password;
}
