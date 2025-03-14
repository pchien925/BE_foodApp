package com.foodApp.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OptionValueRequest {
    @NotBlank(message = "Option value name is required")
    private String name;

    private String description;

    @Min(value = 0, message = "Invalid extra cost")
    private Double extraCost;
    private Boolean available;
    private Boolean defaultOption;
}
