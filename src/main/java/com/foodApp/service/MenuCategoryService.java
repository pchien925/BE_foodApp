package com.foodApp.service;

import com.foodApp.dto.request.MenuCategoryRequest;
import com.foodApp.dto.response.ComboResponse;
import com.foodApp.dto.response.MenuCategoryResponse;
import com.foodApp.dto.response.MenuItemResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.entity.MenuCategory;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuCategoryService {
    MenuCategoryResponse getMenuCategory(Long id);

    MenuCategoryResponse createMenuCategory(MenuCategoryRequest request);

    MenuCategoryResponse updateMenuCategory(Long id, MenuCategoryRequest request);

    void deleteMenuCategory(Long id);

    MenuCategory findById(Long id);

    List<MenuCategoryResponse> getMenuCategories();

    PageResponse<MenuCategoryResponse> getMenuCategories(Integer page, Integer size, String sort, String direction);

    List<MenuItemResponse> getMenuItems(Long id);

    PageResponse<MenuItemResponse> getMenuItems(Long id, Integer page, Integer size, String sort, String direction);

    PageResponse<ComboResponse> getCombos(Long id, Integer page, Integer size, String sort, String direction);
}
