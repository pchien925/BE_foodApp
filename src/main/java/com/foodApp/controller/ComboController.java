package com.foodApp.controller;

import com.foodApp.dto.request.ComboItemRequest;
import com.foodApp.dto.request.ComboRequest;
import com.foodApp.dto.response.ComboItemResponse;
import com.foodApp.dto.response.ComboResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.dto.response.ResponseData;
import com.foodApp.service.ComboItemService;
import com.foodApp.service.ComboService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/combos")
@RequiredArgsConstructor
public class ComboController {
    private final ComboService comboService;
    private final ComboItemService comboItemService;

    @GetMapping("/{id}")
    public ResponseData<ComboResponse> get(@PathVariable @Min(value = 0, message = "Invalid combo ID") Long id){
        return ResponseData.<ComboResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Combo retrieved")
                .data(comboService.getCombo(id))
                .build();
    }

    @PostMapping
    public ResponseData<ComboResponse> create(@RequestBody ComboRequest request){
        return ResponseData.<ComboResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Combo created")
                .data(comboService.createCombo(request))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<ComboResponse> update(@PathVariable @Min(value = 0, message = "Invalid combo ID") Long id, @RequestBody ComboRequest request){
        return ResponseData.<ComboResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Combo updated")
                .data(comboService.updateCombo(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> delete(@PathVariable @Min(value = 0, message = "Invalid combo ID") Long id){
        comboService.deleteCombo(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Combo deleted")
                .build();
    }

    @GetMapping
    public ResponseData<PageResponse<ComboResponse>> getCombos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction

    ){
        return ResponseData.<PageResponse<ComboResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Combos retrieved")
                .data(comboService.getCombos(page, size, sort, direction))
                .build();
    }

    @GetMapping("/search")
    public ResponseData<PageResponse<ComboResponse>> searchCombos(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String menuCategory,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ){
        return ResponseData.<PageResponse<ComboResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Combos retrieved")
                .data(comboService.searchCombos(query, menuCategory, minPrice, maxPrice, page, size, sort, direction))
                .build();
    }

    @PostMapping("/{id}/combo-items")
    public ResponseData<ComboItemResponse> createComboItem(
            @PathVariable @Min(value = 0, message = "Invalid combo ID") Long id,
            @RequestBody ComboItemRequest request
    ){
        return ResponseData.<ComboItemResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Combo item created")
                .data(comboItemService.createComboItem(id, request))
                .build();
    }

    @PutMapping("/{id}/combo-items/{comboItemId}")
    public ResponseData<ComboItemResponse> updateComboItem(
            @PathVariable @Min(value = 0, message = "Invalid combo ID") Long id,
            @PathVariable @Min(value = 0, message = "Invalid combo item ID") Long comboItemId,
            @RequestBody ComboItemRequest request
    ){
        return ResponseData.<ComboItemResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Combo item updated")
                .data(comboItemService.updateComboItem(id, comboItemId, request))
                .build();
    }

    @DeleteMapping("/{id}/combo-items/{comboItemId}")
    public ResponseData<Void> deleteComboItem(
            @PathVariable @Min(value = 0, message = "Invalid combo ID") Long id,
            @PathVariable @Min(value = 0, message = "Invalid combo item ID") Long comboItemId
    ){
        comboItemService.deleteComboItem(id, comboItemId);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Combo item deleted")
                .build();
    }

    @GetMapping("/{id}/combo-items")
    public ResponseData<List<ComboItemResponse>> getComboItems(@PathVariable @Min(value = 0, message = "Invalid combo ID") Long id){
        return ResponseData.<List<ComboItemResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Combo items retrieved")
                .data(comboItemService.getComboItems(id))
                .build();
    }
}
