package com.foodApp.controller;

import com.foodApp.dto.request.OptionTypeRequest;
import com.foodApp.dto.request.OptionValueRequest;
import com.foodApp.dto.response.OptionTypeResponse;
import com.foodApp.dto.response.OptionValueResponse;
import com.foodApp.dto.response.ResponseData;
import com.foodApp.service.OptionTypeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/option-types")
@RequiredArgsConstructor
public class OptionTypeController {
    private final OptionTypeService optionTypeService;

    @GetMapping("/{id}")
    public ResponseData<OptionTypeResponse> getOptionType(
            @PathVariable @Min(value = 0, message = "Invalid option type ID") Long id) {
        return ResponseData.<OptionTypeResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Option type retrieved")
                .data(optionTypeService.getOptionType(id))
                .build();
    }

    @PostMapping
    public ResponseData<OptionTypeResponse> createOptionType(
            @RequestBody @Valid OptionTypeRequest request) {
        return ResponseData.<OptionTypeResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Option type created")
                .data(optionTypeService.createOptionType(request))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<OptionTypeResponse> updateOptionType(
            @PathVariable @Min(value = 0, message = "Invalid option type ID") Long id,
            @RequestBody @Valid OptionTypeRequest request) {
        return ResponseData.<OptionTypeResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Option type updated")
                .data(optionTypeService.updateOptionType(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteOptionType(
            @PathVariable @Min(value = 0, message = "Invalid option type ID") Long id) {
        optionTypeService.deleteOptionType(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Option type deleted")
                .build();
    }

    @GetMapping
    public ResponseData<List<OptionTypeResponse>> getAllOptionTypes() {
        return ResponseData.<List<OptionTypeResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Option types retrieved")
                .data(optionTypeService.getAllOptionTypes())
                .build();
    }

    @PostMapping("/{id}/option-values")
    public ResponseData<OptionValueResponse> createOptionValue(
            @PathVariable @Min(value = 0, message = "Invalid option type ID") Long id,
            @RequestBody @Valid OptionValueRequest request) {
        return ResponseData.<OptionValueResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Option value created")
                .data(optionTypeService.createOptionValue(id, request))
                .build();
    }

    @PutMapping("/{id}/option-values/{valueId}")
    public ResponseData<OptionValueResponse> updateOptionValue(
            @PathVariable @Min(value = 0, message = "Invalid option type ID") Long id,
            @PathVariable @Min(value = 0, message = "Invalid option value ID") Long valueId,
            @RequestBody @Valid OptionValueRequest request) {
        return ResponseData.<OptionValueResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Option value updated")
                .data(optionTypeService.updateOptionValue(id, valueId, request))
                .build();
    }

    @DeleteMapping("/{id}/option-values/{valueId}")
    public ResponseData<Void> deleteOptionValue(
            @PathVariable @Min(value = 0, message = "Invalid option type ID") Long id,
            @PathVariable @Min(value = 0, message = "Invalid option value ID") Long valueId) {
        optionTypeService.deleteOptionValue(id, valueId);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Option value deleted")
                .build();
    }

    @GetMapping("/{id}/option-values")
    public ResponseData<List<OptionValueResponse>> getOptionValues(
            @PathVariable @Min(value = 0, message = "Invalid option type ID") Long id) {
        return ResponseData.<List<OptionValueResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Option values retrieved")
                .data(optionTypeService.getOptionValues(id))
                .build();
    }

}
