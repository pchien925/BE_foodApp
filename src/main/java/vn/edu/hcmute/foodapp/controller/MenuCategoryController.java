package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.MenuCategoryRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuCategoryResponse;
import vn.edu.hcmute.foodapp.dto.response.MenuItemResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.MenuCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-categories")
@RequiredArgsConstructor
@Tag(name = "Menu Category", description = "Menu Category API")
@Slf4j(topic = "MENU_CATEGORY_CONTROLLER")
public class MenuCategoryController {
    private final MenuCategoryService menuCategoryService;

    @PostMapping
    @Operation(summary = "Create a new menu category", description = "Create a new menu category with provided details")
    public ResponseData<MenuCategoryResponse> createMenuCategory(@RequestBody @Valid MenuCategoryRequest request) {
        log.info("Create menu category request");
        return ResponseData.<MenuCategoryResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Menu category created successfully")
                .data(menuCategoryService.createMenuCategory(request))
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a menu category", description = "Update an existing menu category by ID")
    public ResponseData<MenuCategoryResponse> updateMenuCategory(
            @PathVariable Integer id,
            @RequestBody @Valid MenuCategoryRequest request) {
        log.info("Update menu category request for id: {}", id);
        return ResponseData.<MenuCategoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Menu category updated successfully")
                .data(menuCategoryService.updateMenuCategory(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a menu category", description = "Delete a menu category by ID")
    public ResponseData<Void> deleteMenuCategory(@PathVariable Integer id) {
        log.info("Delete menu category request for id: {}", id);
        menuCategoryService.deleteMenuCategory(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Menu category deleted successfully")
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a menu category", description = "Get a menu category by ID")
    public ResponseData<MenuCategoryResponse> getMenuCategory(@PathVariable Integer id) {
        log.info("Get menu category request for id: {}", id);
        return ResponseData.<MenuCategoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Menu category retrieved successfully")
                .data(menuCategoryService.getMenuCategoryById(id))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all menu categories", description = "Get all menu categories with pagination and sorting")
    public ResponseData<PageResponse<MenuCategoryResponse>> getAllMenuCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get all menu categories request with page: {}, size: {}, sort: {}, direction: {}",
                page, size, sort, direction);
        return ResponseData.<PageResponse<MenuCategoryResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Menu categories retrieved successfully")
                .data(menuCategoryService.getMenuCategories(page, size, sort, direction))
                .build();
    }

    @GetMapping("/all")
    @Operation(summary = "Get all menu categories without pagination", description = "Get all menu categories without pagination")
    public ResponseData<List<MenuCategoryResponse>> getAllMenuCategoriesWithoutPagination() {
        log.info("Get all menu categories without pagination request");
        return ResponseData.<List<MenuCategoryResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Menu categories retrieved successfully")
                .data(menuCategoryService.getAllMenuCategories())
                .build();
    }

    @GetMapping("/{id}/menu-items")
    @Operation(summary = "Get all menu items in a category", description = "Get all menu items in a specific category by category ID")
    public ResponseData<PageResponse<MenuItemResponse>> getMenuItemsByCategory(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get menu items by category request for category id: {}, page: {}, size: {}, sort: {}, direction: {}",
                id, page, size, sort, direction);
        return ResponseData.<PageResponse<MenuItemResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Menu items in category retrieved successfully")
                .data(menuCategoryService.getMenuItemsByMenuCategoryId(id, page, size, sort, direction))
                .build();
    }
}
