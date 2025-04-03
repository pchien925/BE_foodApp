package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.MenuItemRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuItemResponse;
import vn.edu.hcmute.foodapp.dto.response.OptionResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-items")
@RequiredArgsConstructor
@Tag(name = "Menu Item", description = "Menu Item API")
@Slf4j(topic = "MENU_ITEM_CONTROLLER")
public class MenuItemController {
    private final MenuItemService menuItemService;

    @PostMapping
    @Operation(summary = "Create a new menu item", description = "Create a new menu item with provided details")
    public ResponseData<MenuItemResponse> createMenuItem(@RequestBody @Valid MenuItemRequest request) {
        log.info("Create menu item request: {}", request); // Consider logging sensitivity
        return ResponseData.<MenuItemResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Menu item created successfully")
                .data(menuItemService.createMenuItem(request))
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a menu item", description = "Update an existing menu item by its ID")
    public ResponseData<MenuItemResponse> updateMenuItem(
            @PathVariable("id") Long id,
            @RequestBody @Valid MenuItemRequest request) {
        log.info("Update menu item request for id: {}", id);
        return ResponseData.<MenuItemResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Menu item updated successfully")
                .data(menuItemService.updateMenuItem(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a menu item", description = "Delete a menu item by its ID")
    public ResponseData<Void> deleteMenuItem(@PathVariable("id") Long id) {
        log.info("Delete menu item request for id: {}", id);
        menuItemService.deleteMenuItem(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Menu item deleted successfully")
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get menu item by ID", description = "Retrieve a menu item by its ID")
    public ResponseData<MenuItemResponse> getMenuItemById(@PathVariable("id") Long id) {
        log.info("Get menu item request for id: {}", id);
        return ResponseData.<MenuItemResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Menu item retrieved successfully")
                .data(menuItemService.getMenuItemById(id))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all menu items", description = "Retrieve a paginated list of menu items, optionally filtered by availability")
    public ResponseData<PageResponse<MenuItemResponse>> getMenuItems(
            @Parameter(description = "Filter by availability status (true/false)") @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get menu items request: isAvailable={}, page={}, size={}, sort={}, direction={}", isAvailable, page, size, sort, direction);
        return ResponseData.<PageResponse<MenuItemResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Menu items retrieved successfully")
                .data(menuItemService.getMenuItems(isAvailable, page, size, sort, direction))
                .build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search menu items", description = "Search menu items by keyword with pagination, optionally filtered by availability")
    public ResponseData<PageResponse<MenuItemResponse>> searchMenuItems(
            @Parameter(description = "Keyword to search in name or description") @RequestParam(required = false) String keyword,
            @Parameter(description = "Menu category ID to filter by") @RequestParam(required = false) Integer menuCategoryId,
            @Parameter(description = "Filter by availability status (true/false)") @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Search menu items request: keyword={}, isAvailable={}, page={}, size={}, sort={}, direction={}", keyword, isAvailable, page, size, sort, direction);
        return ResponseData.<PageResponse<MenuItemResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Menu items search completed successfully")
                .data(menuItemService.searchMenuItems(isAvailable, menuCategoryId, keyword, page, size, sort, direction))
                .build();
    }

    @GetMapping("/{id}/options")
    @Operation(summary = "Get options for a menu item", description = "Retrieve all options for a specific menu item by its ID")
    public ResponseData<List<OptionResponse>> getOptionsByMenuItemId(@PathVariable("id") Long id) {
        log.info("Get options for menu item request for id: {}", id);
        return ResponseData.<List<OptionResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Options retrieved successfully")
                .data(menuItemService.getOptionsByMenuItemId(id))
                .build();
    }
}