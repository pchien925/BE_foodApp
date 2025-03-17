package com.foodApp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OptionValueResponse {
    private Long id;
    private String name;
    private String description;
    private Double extraCost;
    private Boolean available;
    private Boolean defaultOption;
}
