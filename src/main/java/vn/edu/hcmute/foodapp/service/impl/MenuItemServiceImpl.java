package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.hcmute.foodapp.dto.request.MenuItemRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuItemResponse;
import vn.edu.hcmute.foodapp.dto.response.OptionResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.entity.MenuCategory;
import vn.edu.hcmute.foodapp.entity.MenuItem;
import vn.edu.hcmute.foodapp.entity.MenuItemOption;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.MenuItemMapper;
import vn.edu.hcmute.foodapp.mapper.MenuItemOptionMapper;
import vn.edu.hcmute.foodapp.repository.MenuCategoryRepository;
import vn.edu.hcmute.foodapp.repository.MenuItemOptionRepository;
import vn.edu.hcmute.foodapp.repository.MenuItemRepository;
import vn.edu.hcmute.foodapp.service.MenuItemService;
import vn.edu.hcmute.foodapp.util.PaginationUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemOptionRepository menuItemOptionRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    @Override
    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        log.info("Create MenuItem with name: {}", request.getName());
        MenuCategory menuCategory = menuCategoryRepository.findById(request.getMenuCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu category not found with id: " + request.getMenuCategoryId()));

        MenuItem menuItem = MenuItemMapper.INSTANCE.toEntity(request);
        menuItem.setMenuCategory(menuCategory);
        return MenuItemMapper.INSTANCE.toResponse(menuItemRepository.save(menuItem));
    }

    @Override
    public MenuItemResponse updateMenuItem(Long id, MenuItemRequest request) {
        log.info("Update MenuItem with id: {}", id);
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));

        MenuCategory menuCategory = menuCategoryRepository.findById(request.getMenuCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu category not found with id: " + request.getMenuCategoryId()));

        MenuItemMapper.INSTANCE.update(request, menuItem);

        menuItem.setMenuCategory(menuCategory);
        return MenuItemMapper.INSTANCE.toResponse(menuItemRepository.save(menuItem));
    }

    @Override
    public void deleteMenuItem(Long id) {
        log.info("Delete MenuItem with id: {}", id);
        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu item not found with id: " + id);
        }
        menuItemRepository.deleteById(id);
    }

    @Override
    public MenuItemResponse getMenuItemById(Long id) {
        log.info("Get MenuItem with id: {}", id);
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        return MenuItemMapper.INSTANCE.toResponse(menuItem);
    }

    @Override
    public PageResponse<MenuItemResponse> getMenuItems(Boolean isAvailable, int page, int size, String sort, String direction) {
        log.info("Fetching all menu items with pagination: page={}, size={}, sort={}, direction={}", page, size, sort, direction);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<MenuItem> menuItemPage = menuItemRepository.findByIsAvailable(isAvailable, pageable);

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

    @Override
    public PageResponse<MenuItemResponse> searchMenuItems(Boolean isAvailable, Integer menuCategoryId, String keyword, int page, int size, String sort, String direction) {
        log.info("Searching menu items with keyword: {}, page={}, size={}, sort={}, direction={}", keyword, page, size, sort, direction);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<MenuItem> menuItemPage = menuItemRepository.searchMenuItems(keyword, menuCategoryId, isAvailable, pageable);

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

    @Override
    public List<OptionResponse> getOptionsByMenuItemId(Long menuItemId) {
        log.info("Get options by menu item id: {}", menuItemId);

        if (!menuItemRepository.existsById(menuItemId)) {
            throw new ResourceNotFoundException("Menu item not found with id: " + menuItemId);
        }

        List<MenuItemOption> menuItemOptions = menuItemOptionRepository.findByMenuItem_Id(menuItemId);
        Map<Integer, OptionResponse> optionResponseMap = new LinkedHashMap<>();

        for (MenuItemOption menuItemOption : menuItemOptions) {
            Integer optionId = menuItemOption.getOption().getId();
            optionResponseMap.computeIfAbsent(optionId, k -> OptionResponse.builder()
                    .id(optionId)
                    .name(menuItemOption.getOption().getName())
                    .description(menuItemOption.getOption().getDescription())
                    .menuItemOption(new ArrayList<>())
                    .build()
            ).getMenuItemOption().add(MenuItemOptionMapper.INSTANCE.toResponse(menuItemOption));
        }

        return new ArrayList<>(optionResponseMap.values());
    }
}
