package com.foodApp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foodApp.entity.OptionValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionTypeResponse {
    private Long id;
    private String name;
    private String description;

    private Set<OptionValueResponse> optionValues;
}
