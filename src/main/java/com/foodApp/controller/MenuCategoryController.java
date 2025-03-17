package com.foodApp.controller;

import com.foodApp.dto.request.MenuCategoryRequest;
import com.foodApp.dto.response.*;
import com.foodApp.service.MenuCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-categories")
@RequiredArgsConstructor
public class MenuCategoryController {
    private final MenuCategoryService menuCategoryService;

    @PostMapping
    public ResponseData<MenuCategoryResponse> createMenuCategory(@RequestBody @Valid MenuCategoryRequest request) {
        return ResponseData.<MenuCategoryResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Menu category created")
                .data(menuCategoryService.createMenuCategory(request))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<MenuCategoryResponse> getMenuCategory(@PathVariable @Min(value = 0, message = "Invalid category ID") Long id) {
        return ResponseData.<MenuCategoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Menu category found")
                .data(menuCategoryService.getMenuCategory(id))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<MenuCategoryResponse> updateMenuCategory(@PathVariable @Min(value = 0, message = "Invalid category ID") Long id, @RequestBody @Valid MenuCategoryRequest request) {
        return ResponseData.<MenuCategoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Menu category updated")
                .data(menuCategoryService.updateMenuCategory(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<String> deleteMenuCategory(@PathVariable @Min(value = 0, message = "Invalid category ID") Long id) {
        menuCategoryService.deleteMenuCategory(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Menu category deleted");
    }

    @GetMapping
    public ResponseData<PageResponse<MenuCategoryResponse>> getAllMenuCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseData.<PageResponse<MenuCategoryResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Menu categories retrieved")
                .data(menuCategoryService.getMenuCategories(page, size, sort, direction))
                .build();
    }

    @GetMapping("/{id}/menu-items")
    public ResponseData<PageResponse<MenuItemResponse>> getMenuItems(@PathVariable @Min(value = 0, message = "Invalid category ID") Long id,
                                                             @RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "name") String sort,
                                                             @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseData.<PageResponse<MenuItemResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Menu items retrieved")
                .data(menuCategoryService.getMenuItems(id, page, size, sort, direction))
                .build();
    }
}
