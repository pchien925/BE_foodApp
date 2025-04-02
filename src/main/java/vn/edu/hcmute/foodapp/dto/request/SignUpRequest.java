package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.edu.hcmute.foodapp.util.EnumValue;
import vn.edu.hcmute.foodapp.util.PhoneNumber;
import vn.edu.hcmute.foodapp.util.enumeration.EGender;

import java.time.LocalDate;

@Getter
public class SignUpRequest {
    @NotEmpty(message = "fullName must not be null")
    private String fullName;

    @Email(message = "email invalid format")
    @NotEmpty(message = "email must not be null")
    private String email;

    @NotBlank(message = "phone must not be null")
    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotNull(message = "gender must not be null")
    @EnumValue(name = "gender", enumClass = EGender.class)
    private String gender;

    @NotNull(message = "dateOfBirth must not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dob;

    private String address;

    @NotBlank(message = "password must not be null")
    private String password;

    @NotBlank(message = "confirmPassword must not be null")
    private String confirmPassword;
}
