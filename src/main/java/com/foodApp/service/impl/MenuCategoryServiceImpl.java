package com.foodApp.service.impl;

import com.foodApp.dto.request.MenuCategoryRequest;
import com.foodApp.dto.response.ComboResponse;
import com.foodApp.dto.response.MenuCategoryResponse;
import com.foodApp.dto.response.MenuItemResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.entity.Combo;
import com.foodApp.entity.MenuCategory;
import com.foodApp.entity.MenuItem;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.ComboMapper;
import com.foodApp.mapper.MenuCategoryMapper;
import com.foodApp.mapper.MenuItemMapper;
import com.foodApp.repository.ComboRepository;
import com.foodApp.repository.MenuCategoryRepository;
import com.foodApp.repository.MenuItemRepository;
import com.foodApp.service.MenuCategoryService;
import com.foodApp.util.PaginationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuCategoryServiceImpl implements MenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuCategoryMapper menuCategoryMapper;
    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;
    private final ComboRepository comboRepository;
    private final ComboMapper comboMapper;


    @Override
    public MenuCategoryResponse getMenuCategory(Long id) {
        return menuCategoryMapper.toResponse(findById(id));
    }

    @Transactional
    @Override
    public MenuCategoryResponse createMenuCategory(MenuCategoryRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        MenuCategory menuCategory = menuCategoryMapper.toEntity(request);
        return menuCategoryMapper.toResponse(menuCategoryRepository.save(menuCategory));
    }

    @Transactional
    @Override
    public MenuCategoryResponse updateMenuCategory(Long id, MenuCategoryRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        MenuCategory menuCategory = findById(id);
        menuCategoryMapper.update(request, menuCategory);
        return menuCategoryMapper.toResponse(menuCategoryRepository.save(menuCategory));
    }

    @Transactional
    @Override
    public void deleteMenuCategory(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        MenuCategory menuCategory = findById(id);
        menuCategoryRepository.delete(menuCategory);
    }

    @Override
    public MenuCategory findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return menuCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu category not found with id: " + id));
    }

    @Override
    public List<MenuCategoryResponse> getMenuCategories() {
        List<MenuCategory> menuCategories = menuCategoryRepository.findAll();

        return menuCategories.stream().map(menuCategoryMapper::toResponse).toList();
    }

    @Override
    public PageResponse<MenuCategoryResponse> getMenuCategories(Integer page, Integer size, String sort, String direction) {
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<MenuCategory> categoryPage = menuCategoryRepository.findAll(pageable);

        return PageResponse.<MenuCategoryResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(categoryPage.getTotalPages())
                .totalElements(categoryPage.getTotalElements())
                .content(categoryPage.getContent().stream().map(menuCategoryMapper::toResponse).toList())
                .build();
    }

    @Override
    public List<MenuItemResponse> getMenuItems(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        List<MenuItem> items = menuItemRepository.findByMenuCategory_Id(id);
        return items.stream().map(menuItemMapper::toResponse).toList();
    }

    @Override
    public PageResponse<MenuItemResponse> getMenuItems(Long id, Integer page, Integer size, String sort, String direction) {
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<MenuItem> menuItemPage = menuItemRepository.findByMenuCategory_Id(id, pageable);

        return PageResponse.<MenuItemResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(menuItemPage.getTotalPages())
                .totalElements(menuItemPage.getTotalElements())
                .content(menuItemPage.getContent().stream().map(menuItemMapper::toResponse).toList())
                .build();

    }


    @Override
    public PageResponse<ComboResponse> getCombos(Long id, Integer page, Integer size, String sort, String direction) {
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Combo> comboPage = comboRepository.findByMenuCategoryId(id, pageable);

        return PageResponse.<ComboResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(comboPage.getTotalPages())
                .totalElements(comboPage.getTotalElements())
                .content(comboPage.getContent().stream().map(comboMapper::toResponse).toList())
                .build();
    }
}
