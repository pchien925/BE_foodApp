package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.hcmute.foodapp.dto.request.MenuCategoryRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuCategoryResponse;
import vn.edu.hcmute.foodapp.dto.response.MenuItemResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.entity.MenuCategory;
import vn.edu.hcmute.foodapp.entity.MenuItem;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.MenuCategoryMapper;
import vn.edu.hcmute.foodapp.mapper.MenuItemMapper;
import vn.edu.hcmute.foodapp.repository.MenuCategoryRepository;
import vn.edu.hcmute.foodapp.repository.MenuItemRepository;
import vn.edu.hcmute.foodapp.service.MenuCategoryService;
import vn.edu.hcmute.foodapp.util.PaginationUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuCategoryServiceImpl implements MenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public MenuCategoryResponse createMenuCategory(MenuCategoryRequest request) {
        log.info("Create MenuCategory with name: {}", request.getName());
        MenuCategory menuCategory = MenuCategoryMapper.INSTANCE.toEntity(request);

        return MenuCategoryMapper.INSTANCE.toResponse(menuCategoryRepository.save(menuCategory));
    }

    @Override
    public MenuCategoryResponse updateMenuCategory(Integer id, MenuCategoryRequest request) {
        log.info("Update MenuCategory with id: {}", id);
        MenuCategory menuCategory = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu category not found with id: " + id));

        MenuCategoryMapper.INSTANCE.update(request, menuCategory);

        return MenuCategoryMapper.INSTANCE.toResponse(menuCategoryRepository.save(menuCategory));
    }

    @Override
    public void deleteMenuCategory(Integer id) {
        log.info("Delete MenuCategory with id: {}", id);
        if (!menuCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu category not found with id: " + id);
        }
        menuCategoryRepository.deleteById(id);
    }

    @Override
    public MenuCategoryResponse getMenuCategoryById(Integer id) {
        log.info("Get MenuCategory with id: {}", id);
        MenuCategory menuCategory = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu category not found with id: " + id));
        return MenuCategoryMapper.INSTANCE.toResponse(menuCategory);
    }

    @Override
    public PageResponse<MenuCategoryResponse> getMenuCategories(int page, int size, String sort, String direction) {
        log.info("Fetching all branches with pagination: page={}, size={}, sort={}, direction={}", page, size, sort, direction);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<MenuCategory> menuCategoryPage = menuCategoryRepository.findAll(pageable);

        return PageResponse.<MenuCategoryResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(menuCategoryPage.getTotalPages())
                .totalElements(menuCategoryPage.getTotalElements())
                .content(menuCategoryPage.getContent().stream().map(MenuCategoryMapper.INSTANCE::toResponse).toList())
                .build();
    }

    @Override
    public List<MenuCategoryResponse> getAllMenuCategories() {
        log.info("Fetching all menu categories");
        return menuCategoryRepository.findAll().stream().map(MenuCategoryMapper.INSTANCE::toResponse).toList();
    }

    @Override
    public PageResponse<MenuItemResponse> getMenuItemsByMenuCategoryId(Integer menuCategoryId, int page, int size, String sort, String direction) {
        log.info("Fetching all menu items with pagination: page={}, size={}, sort={}, direction={}", page, size, sort, direction);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<MenuItem> menuItemPage = menuItemRepository.findByIsAvailableTrue(menuCategoryId, pageable);

        return PageResponse.<MenuItemResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(menuItemPage.getTotalPages())
                .totalElements(menuItemPage.getTotalElements())
                .content(menuItemPage.getContent().stream()
                        .map(MenuItemMapper.INSTANCE::toResponse)
                        .toList())
                .build();
    }
}
