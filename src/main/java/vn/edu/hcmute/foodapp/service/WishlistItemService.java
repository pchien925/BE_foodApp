package vn.edu.hcmute.foodapp.service;


import vn.edu.hcmute.foodapp.dto.response.MenuItemResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;

public interface WishlistItemService {
    PageResponse<MenuItemResponse> getWishlistItemByUserId(Long userId, int page, int size, String sort, String direction);

    MenuItemResponse createWishlistItem(Long userId, Long menuItemId);

    void deleteWishlistItem(Long userId, Long menuItemId);

    MenuItemResponse getWishlistItemById(Long userId, Long menuItemId);
}
