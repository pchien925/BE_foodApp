package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String avatar;
    private String gender;
    private String address;
    private String status;
    private Integer loyaltyPointsBalance;

    private List<String> roles;
}
