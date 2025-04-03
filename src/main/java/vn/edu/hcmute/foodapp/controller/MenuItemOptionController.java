package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.MenuItemOptionRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuItemOptionResponse;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.MenuItemOptionService;

@RestController
@RequestMapping("/api/v1/menu-item-options")
@RequiredArgsConstructor
@Tag(name = "Menu Item Option", description = "API for managing associations between Menu Items and Options")
@Slf4j(topic = "MENU_ITEM_OPTION_CONTROLLER")
public class MenuItemOptionController {

    private final MenuItemOptionService menuItemOptionService;

    @PostMapping
    @Operation(summary = "Link a Menu Item with an Option", description = "Create a new association between a menu item and an option.")
    public ResponseData<MenuItemOptionResponse> createMenuItemOption(@RequestBody @Valid MenuItemOptionRequest request) {
        // Log request carefully if it contains sensitive info, though IDs are usually fine.
        log.info("Request to create MenuItemOption association: menuItemId={}, optionId={}", request.getMenuItemId(), request.getOptionId());
        MenuItemOptionResponse response = menuItemOptionService.createMenuItemOption(request);
        return ResponseData.<MenuItemOptionResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Menu item option association created successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Menu Item Option association", description = "Update an existing association by its ID. Note: Usually you delete and create a new one for associations, but update is provided if needed for specific fields on the link table itself.")
    public ResponseData<MenuItemOptionResponse> updateMenuItemOption(
            @PathVariable("id") Integer id,
            @RequestBody @Valid MenuItemOptionRequest request) {
        log.info("Request to update MenuItemOption association with id: {}", id);
        MenuItemOptionResponse response = menuItemOptionService.updateMenuItemOption(id, request);
        return ResponseData.<MenuItemOptionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Menu item option association updated successfully")
                .data(response)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Unlink a Menu Item from an Option", description = "Delete the association between a menu item and an option by the association ID.")
    public ResponseData<Void> deleteMenuItemOption(@PathVariable("id") Integer id) {
        log.info("Request to delete MenuItemOption association with id: {}", id);
        menuItemOptionService.deleteMenuItemOption(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Menu item option association deleted successfully")
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Menu Item Option association by ID", description = "Retrieve details of a specific menu item-option association by its ID.")
    public ResponseData<MenuItemOptionResponse> getMenuItemOptionById(@PathVariable("id") Integer id) {
        log.info("Request to get MenuItemOption association with id: {}", id);
        MenuItemOptionResponse response = menuItemOptionService.getMenuItemOptionById(id);
        return ResponseData.<MenuItemOptionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Menu item option association retrieved successfully")
                .data(response)
                .build();
    }
}