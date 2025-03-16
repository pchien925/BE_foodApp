package com.foodApp.service.impl;

import com.foodApp.dto.request.AddOptionTypesRequest;
import com.foodApp.dto.request.MenuItemRequest;
import com.foodApp.dto.response.MenuItemResponse;
import com.foodApp.dto.response.OptionTypeResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.entity.MenuCategory;
import com.foodApp.entity.MenuItem;
import com.foodApp.entity.OptionType;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.MenuItemMapper;
import com.foodApp.repository.MenuItemRepository;
import com.foodApp.repository.OptionTypeRepository;
import com.foodApp.service.MenuCategoryService;
import com.foodApp.service.MenuItemService;
import com.foodApp.util.PaginationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuCategoryService menuCategoryService;
    private final MenuItemMapper menuItemMapper;
    private final OptionTypeRepository optionTypeRepository;

    @Override
    public MenuItemResponse getMenuItem(Long id) {
        return menuItemMapper.toResponse(findById(id));
    }

    @Transactional
    @Override
    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        MenuItem menuItem = menuItemMapper.toEntity(request);

        if (request.getMenuCategoryId() != null) {
            MenuCategory category = menuCategoryService.findById(request.getMenuCategoryId());
            menuItem.setMenuCategory(category);
        }

        return menuItemMapper.toResponse(menuItemRepository.save(menuItem));
    }

    @Transactional
    @Override
    public MenuItemResponse updateMenuItem(Long id, MenuItemRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Menu item not found with id " + id)
        );

        menuItemMapper.update(request, menuItem);

        if (request.getMenuCategoryId() != null) {
            MenuCategory category = menuCategoryService.findById(request.getMenuCategoryId());
            menuItem.setMenuCategory(category);
        }

        return menuItemMapper.toResponse(menuItemRepository.save(menuItem));
    }

    @Transactional
    @Override
    public void deleteMenuItem(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        MenuItem menuItem = findById(id);

        menuItemRepository.delete(menuItem);
    }

    @Override
    public MenuItem findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
    }

    @Override
    public PageResponse<MenuItemResponse> getMenuItems(int page, int size, String sort, String direction) {
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<MenuItem> menuItemPage = menuItemRepository.findAll(pageable);

        return PageResponse.<MenuItemResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(menuItemPage.getTotalPages())
                .totalElements(menuItemPage.getTotalElements())
                .content(menuItemPage.getContent().stream().map(menuItemMapper::toResponse).toList())
                .build();
    }

    @Override
    public PageResponse<MenuItemResponse> searchMenuItems(String query, String menuCategory, Double minPrice, Double maxPrice, int page, int size, String sort, String direction) {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
        }

        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<MenuItem> menuItemPage = menuItemRepository.searchMenuItems(query, menuCategory, minPrice, maxPrice, pageable);
        return PageResponse.<MenuItemResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(menuItemPage.getTotalPages())
                .totalElements(menuItemPage.getTotalElements())
                .content(menuItemPage.getContent().stream().map(menuItemMapper::toResponse).toList())
                .build();
    }

    @Override
    @Transactional
    public MenuItemResponse addOptionTypes(Long menuItemId, AddOptionTypesRequest request) {
        MenuItem menuItem = findById(menuItemId);

        Set<OptionType> optionTypes = new HashSet<>(optionTypeRepository.findAllById(request.getOptionTypeIds()));

        if (optionTypes.isEmpty()) {
            throw new ResourceNotFoundException("Option types not found with IDs: " + request.getOptionTypeIds());
        }

        // Xử lí trùng lặp
        Set<Long> existingIds = menuItem.getOptionTypes().stream().map(OptionType::getId).collect(Collectors.toSet());
        optionTypes.removeIf(option -> existingIds.contains(option.getId()));

        menuItem.getOptionTypes().addAll(optionTypes);

        return menuItemMapper.toResponse(menuItemRepository.save(menuItem));
    }

    @Transactional
    @Override
    public MenuItemResponse deleteOptionTypes(Long menuItemId, Set<Long> optionTypeIds) {
        if (optionTypeIds == null) {
            throw new IllegalArgumentException("Option type IDs cannot be null");
        }
        if (optionTypeIds.isEmpty()) {
            throw new IllegalArgumentException("Option type IDs list cannot be empty");
        }

        MenuItem menuItem = findById(menuItemId);

        Set<OptionType> currentOptionTypes = menuItem.getOptionTypes();

        // Kiểm tra xem các OptionType cần xóa có tồn tại trong MenuItem không
        Set<Long> existingIds = currentOptionTypes.stream()
                .map(OptionType::getId)
                .collect(Collectors.toSet());

        Set<Long> nonExistingIds = new HashSet<>(optionTypeIds);
        nonExistingIds.removeAll(existingIds);

        if (!nonExistingIds.isEmpty()) {
            throw new ResourceNotFoundException(
                    "The following option type IDs were not found in menu item " + menuItemId + ": " + nonExistingIds
            );
        }

        // Xóa các OptionType khớp với optionTypeIds
        currentOptionTypes.removeIf(option -> optionTypeIds.contains(option.getId()));

        return menuItemMapper.toResponse(menuItemRepository.save(menuItem));
    }
}
