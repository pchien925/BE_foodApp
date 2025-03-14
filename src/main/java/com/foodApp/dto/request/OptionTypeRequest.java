package com.foodApp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionTypeRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
}
