package com.foodApp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Set;

@Getter
public class AddOptionTypesRequest {
    @NotNull(message = "Option type IDs cannot be null")
    private Set<Long> optionTypeIds;
}
