package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendOtpResponse {
    private String email;
    private Integer otpExpiry;
}
