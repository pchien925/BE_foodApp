package vn.edu.hcmute.foodapp.service;

import vn.edu.hcmute.foodapp.dto.request.MenuCategoryRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuCategoryResponse;
import vn.edu.hcmute.foodapp.dto.response.MenuItemResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;

public interface MenuCategoryService {
    MenuCategoryResponse createMenuCategory(MenuCategoryRequest request);

    MenuCategoryResponse updateMenuCategory(Integer id, MenuCategoryRequest request);

    void deleteMenuCategory(Integer id);

    MenuCategoryResponse getMenuCategoryById(Integer id);

    PageResponse<MenuCategoryResponse> getMenuCategories(int page, int size, String sort, String direction);

    PageResponse<MenuItemResponse> getMenuItemsByMenuCategoryId(Integer menuCategoryId, int page, int size, String sort, String direction);
}
