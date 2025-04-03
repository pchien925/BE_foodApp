package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import vn.edu.hcmute.foodapp.util.PhoneNumber;

@Getter
public class BranchRequest {
    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "address must not be null")
    private String address;

    @PhoneNumber(message = "phone number is not valid")
    @NotNull(message = "phone number must not be null")
    private String phone;

    private boolean isActive;

    private String operatingHours;
}
