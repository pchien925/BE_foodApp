package com.foodApp.service;

import com.foodApp.dto.request.AddOptionTypesRequest;
import com.foodApp.dto.request.DelOptionTypesRequest;
import com.foodApp.dto.request.MenuItemRequest;
import com.foodApp.dto.request.OptionTypeRequest;
import com.foodApp.dto.response.MenuCategoryResponse;
import com.foodApp.dto.response.MenuItemResponse;
import com.foodApp.dto.response.OptionTypeResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.entity.MenuItem;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

public interface MenuItemService {
    MenuItemResponse getMenuItem(Long id);

    MenuItemResponse createMenuItem(MenuItemRequest request);

    MenuItemResponse updateMenuItem(Long id, MenuItemRequest request);

    void deleteMenuItem(Long id);

    MenuItem findById(Long id);

    PageResponse<MenuItemResponse> getMenuItems(int page, int size, String sort, String direction);

    PageResponse<MenuItemResponse> searchMenuItems(String query, String menuCategory, Double minPrice, Double maxPrice, int page, int size, String sort, String direction);

    MenuItemResponse addOptionTypes(Long menuItemId, AddOptionTypesRequest request);

    @Transactional
    MenuItemResponse delOptionTypes(Long menuItemId, DelOptionTypesRequest request);
}
