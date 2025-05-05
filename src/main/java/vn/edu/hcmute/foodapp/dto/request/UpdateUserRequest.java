package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import vn.edu.hcmute.foodapp.util.EnumValue;
import vn.edu.hcmute.foodapp.util.PhoneNumber;

import java.time.LocalDate;

@Getter
public class UpdateUserRequest {
    private String fullName;

    @PhoneNumber(message = "phone number is not valid")
    private String phone;

    private LocalDate dob;

    @EnumValue(name = "gender", enumClass = vn.edu.hcmute.foodapp.util.enumeration.EGender.class)
    private String gender;

    private String avatar;

    private String address;
}
