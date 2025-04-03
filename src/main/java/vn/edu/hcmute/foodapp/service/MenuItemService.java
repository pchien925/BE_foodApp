package vn.edu.hcmute.foodapp.service;

import vn.edu.hcmute.foodapp.dto.request.MenuItemRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuItemResponse;
import vn.edu.hcmute.foodapp.dto.response.OptionResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;

import java.util.List;

public interface MenuItemService {
    MenuItemResponse createMenuItem(MenuItemRequest request);

    MenuItemResponse updateMenuItem(Long id, MenuItemRequest request);

    void deleteMenuItem(Long id);

    MenuItemResponse getMenuItemById(Long id);

    PageResponse<MenuItemResponse> getMenuItems(Boolean isAvailable, int page, int size, String sort, String direction);

    PageResponse<MenuItemResponse> searchMenuItems(Boolean isAvailable, Integer menuCategoryId, String keyword, int page, int size, String sort, String direction);

    List<OptionResponse> getOptionsByMenuItemId(Long menuItemId);
}
