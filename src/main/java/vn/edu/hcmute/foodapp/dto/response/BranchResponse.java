package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BranchResponse {
    private Integer id;

    private String name;

    private String address;

    private String phone;

    private boolean isActive;

    private String operatingHours;
}
