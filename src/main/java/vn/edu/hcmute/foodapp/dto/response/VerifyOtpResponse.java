package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerifyOtpResponse {
    private String email;
    private String verificationToken;
}
