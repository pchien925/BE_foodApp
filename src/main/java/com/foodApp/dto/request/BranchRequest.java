package com.foodApp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BranchRequest {
    @NotNull(message = "Branch name cannot be null")
    private String name;
    @NotNull(message = "Branch address cannot be null")
    private String address;

    @NotNull(message = "Branch phone cannot be null")
    private String phone;

    private String latitude;
}
