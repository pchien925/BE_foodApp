package com.foodApp.service;

import com.foodApp.dto.request.AddOptionTypesRequest;
import com.foodApp.dto.request.MenuItemRequest;
import com.foodApp.dto.response.MenuItemResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.entity.MenuItem;

import java.util.Set;

public interface MenuItemService {
    MenuItemResponse getMenuItem(Long id);

    MenuItemResponse createMenuItem(MenuItemRequest request);

    MenuItemResponse updateMenuItem(Long id, MenuItemRequest request);

    void deleteMenuItem(Long id);

    MenuItem findById(Long id);

    PageResponse<MenuItemResponse> getMenuItems(int page, int size, String sort, String direction);

    PageResponse<MenuItemResponse> searchMenuItems(String query, String menuCategory, Double minPrice, Double maxPrice, int page, int size, String sort, String direction);

    MenuItemResponse addOptionTypes(Long menuItemId, AddOptionTypesRequest request);

    MenuItemResponse deleteOptionTypes(Long menuItemId, Set<Long> optionTypeIds);
}
