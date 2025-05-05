package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.WishlistItemRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuItemResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.WishlistItemService;

@RestController
@RequestMapping("/api/v1/wishlist-items")
@RequiredArgsConstructor
@Slf4j(topic = "WISHLIST_ITEM_CONTROLLER")
@Tag(name = "Wishlist Item", description = "Wishlist Item API")
public class WishlistItemController {
    private final WishlistItemService wishlistItemService;

    @GetMapping("/{userId}/{menuItemId}")
    public ResponseData<MenuItemResponse> getWishlistItem(@PathVariable("userId") Long userId, @PathVariable("menuItemId") Long menuItemId) {
        log.info("Fetching wishlist item for user ID: {} and menu item ID: {}", userId, menuItemId);
        MenuItemResponse menuItemResponse = wishlistItemService.getWishlistItemById(userId, menuItemId);
        return ResponseData.<MenuItemResponse>builder()
                .data(menuItemResponse)
                .message("Wishlist item retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseData<MenuItemResponse> createWishlistItem(@RequestBody WishlistItemRequest request) {
        log.info("Creating wishlist item for user ID: {} and menu item ID: {}", request.getUserId(), request.getMenuItemId());
        MenuItemResponse menuItemResponse = wishlistItemService.createWishlistItem(request.getUserId(), request.getMenuItemId());
        return ResponseData.<MenuItemResponse>builder()
                .data(menuItemResponse)
                .message("Wishlist item created successfully")
                .build();
    }

    @DeleteMapping("/{userId}/{menuItemId}")
    public ResponseData<Void> deleteWishlistItem(@PathVariable("userId") Long userId, @PathVariable("menuItemId") Long menuItemId) {
        log.info("Deleting wishlist item for user ID: {} and menu item ID: {}", userId, menuItemId);
        wishlistItemService.deleteWishlistItem(userId, menuItemId);
        return ResponseData.<Void>builder()
                .message("Wishlist item deleted successfully")
                .build();
    }

    @GetMapping
    public ResponseData<PageResponse<MenuItemResponse>> getWishlistItems(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction) {
        log.info("Fetching wishlist items for user ID: {}", userId);
        PageResponse<MenuItemResponse> wishlistItems = wishlistItemService.getWishlistItemByUserId(userId, page, size, sort, direction);
        return ResponseData.<PageResponse<MenuItemResponse>>builder()
                .data(wishlistItems)
                .message("Wishlist items retrieved successfully")
                .build();
    }
}
