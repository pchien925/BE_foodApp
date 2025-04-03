package vn.edu.hcmute.foodapp.service;

import vn.edu.hcmute.foodapp.dto.request.MenuItemOptionRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuItemOptionResponse;

public interface MenuItemOptionService {
    MenuItemOptionResponse createMenuItemOption(MenuItemOptionRequest request);

    MenuItemOptionResponse updateMenuItemOption(Integer id, MenuItemOptionRequest request);

    void deleteMenuItemOption(Integer id);

    MenuItemOptionResponse getMenuItemOptionById(Integer id);
}
