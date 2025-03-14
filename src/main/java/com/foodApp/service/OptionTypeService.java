package com.foodApp.service;

import com.foodApp.dto.request.OptionTypeRequest;
import com.foodApp.dto.request.OptionValueRequest;
import com.foodApp.dto.response.OptionTypeResponse;
import com.foodApp.dto.response.OptionValueResponse;
import com.foodApp.entity.OptionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

public interface OptionTypeService {
    OptionType findById(Long id);

    OptionTypeResponse getOptionType(Long id);

    OptionTypeResponse createOptionType(OptionTypeRequest request);

    OptionTypeResponse updateOptionType(Long id, OptionTypeRequest request);

    void deleteOptionType(Long id);

    List<OptionTypeResponse> getAllOptionTypes();

    OptionValueResponse createOptionValue(@Min(value = 0, message = "Invalid option type ID") Long id, @Valid OptionValueRequest request);

    OptionValueResponse updateOptionValue(Long id, Long valueId, OptionValueRequest request);

    List<OptionValueResponse> getOptionValues(@Min(value = 0, message = "Invalid option type ID") Long id);

    void deleteOptionValue(@Min(value = 0, message = "Invalid option type ID") Long id, @Min(value = 0, message = "Invalid option value ID") Long valueId);
}
