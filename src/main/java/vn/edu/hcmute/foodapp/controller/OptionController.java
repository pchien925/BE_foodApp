package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.OptionRequest;
import vn.edu.hcmute.foodapp.dto.response.OptionResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.OptionService;

@RestController
@RequestMapping("/api/v1/options")
@RequiredArgsConstructor
@Tag(name = "Option", description = "Option API for managing menu item options")
@Slf4j(topic = "OPTION_CONTROLLER")
public class OptionController {

    private final OptionService optionService;

    @PostMapping
    @Operation(summary = "Create a new option", description = "Create a new option with provided details (e.g., name, price).")
    public ResponseData<OptionResponse> createOption(@RequestBody @Valid OptionRequest request) {
        log.info("Create option request: {}", request);
        OptionResponse response = optionService.createOption(request);
        return ResponseData.<OptionResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Option created successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an option", description = "Update an existing option by its ID.")
    public ResponseData<OptionResponse> updateOption(
            @PathVariable("id") Integer id,
            @RequestBody @Valid OptionRequest request) {
        log.info("Update option request for id: {}", id);
        OptionResponse response = optionService.updateOption(id, request);
        return ResponseData.<OptionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Option updated successfully")
                .data(response)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an option", description = "Delete an option by its ID.")
    public ResponseData<Void> deleteOption(@PathVariable("id") Integer id) {
        log.info("Delete option request for id: {}", id);
        optionService.deleteOption(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Option deleted successfully")
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get option by ID", description = "Retrieve a specific option by its ID.")
    public ResponseData<OptionResponse> getOptionById(@PathVariable("id") Integer id) {
        log.info("Get option request for id: {}", id);
        OptionResponse response = optionService.getOptionById(id);
        return ResponseData.<OptionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Option retrieved successfully")
                .data(response)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all options", description = "Retrieve a paginated list of all options.")
    public ResponseData<PageResponse<OptionResponse>> getOptions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get all options request: page={}, size={}, sort={}, direction={}", page, size, sort, direction);
        PageResponse<OptionResponse> response = optionService.getOptions(page, size, sort, direction);
        return ResponseData.<PageResponse<OptionResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Options retrieved successfully")
                .data(response)
                .build();
    }
}