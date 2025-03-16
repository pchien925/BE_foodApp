package com.foodApp.controller;

import com.foodApp.dto.request.AddOptionTypesRequest;
import com.foodApp.dto.request.MenuItemRequest;
import com.foodApp.dto.response.*;
import com.foodApp.service.MenuItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping("/{id}")
    public ResponseData<MenuItemResponse> get(@PathVariable @Min(value = 0, message = "Invalid menu item ID") Long id){
        return ResponseData.<MenuItemResponse>builder()
                .status(200)
                .message("Menu item retrieved")
                .data(menuItemService.getMenuItem(id))
                .build();
    }

    @PostMapping
    public ResponseData<MenuItemResponse> create(@RequestBody @Valid MenuItemRequest request){
        return ResponseData.<MenuItemResponse>builder()
                .status(201)
                .message("Menu item created")
                .data(menuItemService.createMenuItem(request))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<MenuItemResponse> update(@PathVariable @Min(value = 0, message = "Invalid menu item ID") Long id, @RequestBody @Valid MenuItemRequest request){
        return ResponseData.<MenuItemResponse>builder()
                .status(200)
                .message("Menu item updated")
                .data(menuItemService.updateMenuItem(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> delete(@PathVariable @Min(value = 0, message = "Invalid menu item ID") Long id){
        menuItemService.deleteMenuItem(id);
        return ResponseData.<Void>builder()
                .status(204)
                .message("Menu item deleted")
                .build();
    }

    @GetMapping
    public ResponseData<PageResponse<MenuItemResponse>> getMenuItems(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseData.<PageResponse<MenuItemResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Menu categories retrieved")
                .data(menuItemService.getMenuItems(page, size, sort, direction))
                .build();
    }

    @GetMapping("/search")
    public ResponseData<PageResponse<MenuItemResponse>> searchMenuItems(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String menuCategory,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseData.<PageResponse<MenuItemResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Menu items retrieved")
                .data(menuItemService.searchMenuItems(query, menuCategory, minPrice, maxPrice , page, size, sort, direction))
                .build();
    }


    @PostMapping("/{id}/option-types")
    public ResponseData<MenuItemResponse> addOptionTypes(
            @PathVariable @Min(value = 0, message = "Invalid menu item ID") Long id,
            @RequestBody @Valid AddOptionTypesRequest request
    )
    {
        return ResponseData.<MenuItemResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Option types added")
                .data(menuItemService.addOptionTypes(id, request))
                .build();
    }

    @DeleteMapping("/{id}/option-types")
    public ResponseData<MenuItemResponse> deleteOptionTypes(
            @PathVariable @Min(value = 0, message = "Invalid menu item ID") Long id,
            @RequestParam("optionTypeIds") @NotNull(message = "Option type IDs cannot be null") Set<Long> optionTypeIds
    ) {
        return ResponseData.<MenuItemResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Option types deleted")
                .data(menuItemService.deleteOptionTypes(id, optionTypeIds))
                .build();
    }

}
