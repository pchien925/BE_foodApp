package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.hcmute.foodapp.dto.response.MenuItemResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.entity.MenuItem;
import vn.edu.hcmute.foodapp.entity.User;
import vn.edu.hcmute.foodapp.entity.WishlistItem;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.MenuItemMapper;
import vn.edu.hcmute.foodapp.repository.MenuItemRepository;
import vn.edu.hcmute.foodapp.repository.UserRepository;
import vn.edu.hcmute.foodapp.repository.WishlistItemRepository;
import vn.edu.hcmute.foodapp.service.WishlistItemService;
import vn.edu.hcmute.foodapp.util.PaginationUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishlistItemServiceImpl implements WishlistItemService {
    private final WishlistItemRepository wishlistItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    @Override
    public PageResponse<MenuItemResponse> getWishlistItemByUserId(Long userId, int page, int size, String sort, String direction) {
        log.info("Get wishlist item by user id: {}", userId);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<WishlistItem> wishlistItems = wishlistItemRepository.findByUser_Id(userId, pageable);

        List<MenuItemResponse> menuItemResponses = wishlistItems.getContent().stream()
                .map(WishlistItem::getMenuItem)
                .map(MenuItemMapper.INSTANCE::toResponse)
                .toList();
        return PageResponse.<MenuItemResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(wishlistItems.getTotalPages())
                .totalElements(wishlistItems.getTotalElements())
                .content(menuItemResponses)
                .build();
    }

    @Override
    public MenuItemResponse createWishlistItem(Long userId, Long menuItemId) {
        log.info("Create wishlist item for user id: {} and menu item id: {}", userId, menuItemId);
        WishlistItem wishlistItem = new WishlistItem();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId));

        wishlistItem.setUser(user);
        wishlistItem.setMenuItem(menuItem);
        wishlistItemRepository.save(wishlistItem);
        return MenuItemMapper.INSTANCE.toResponse(wishlistItem.getMenuItem());
    }

    @Override
    public void deleteWishlistItem(Long userId, Long menuItemId) {
        log.info("Delete wishlist item for user id: {} and menu item id: {}", userId, menuItemId);
        WishlistItem wishlistItem = wishlistItemRepository.findByUser_IdAndMenuItem_Id(userId, menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found for user id: " + userId + " and menu item id: " + menuItemId));
        wishlistItemRepository.delete(wishlistItem);
    }

    @Override
    public MenuItemResponse getWishlistItemById(Long userId, Long menuItemId) {
        log.info("Get wishlist item by user id: {} and menu item id: {}", userId, menuItemId);
        WishlistItem wishlistItem = wishlistItemRepository.findByUser_IdAndMenuItem_Id(userId, menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found for user id: " + userId + " and menu item id: " + menuItemId));
        return MenuItemMapper.INSTANCE.toResponse(wishlistItem.getMenuItem());
    }
}
