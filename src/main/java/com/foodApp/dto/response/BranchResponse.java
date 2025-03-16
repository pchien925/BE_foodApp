package com.foodApp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BranchResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String latitude;
}
