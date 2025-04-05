package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BranchDetailsResponse {
    private Integer id;

    private String name;

    private String address;

    private String phone;

    private boolean isActive;

    private String operatingHours;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
