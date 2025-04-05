package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BranchInfoResponse {
    private Integer id;

    private String name;

    private String address;

    private String phone;
}
