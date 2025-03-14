package com.foodApp.service.impl;

import com.foodApp.dto.request.ComboRequest;
import com.foodApp.dto.response.ComboResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.entity.Combo;
import com.foodApp.entity.MenuCategory;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.ComboMapper;
import com.foodApp.repository.ComboRepository;
import com.foodApp.service.ComboService;
import com.foodApp.service.MenuCategoryService;
import com.foodApp.util.PaginationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl implements ComboService {
    private final ComboRepository comboRepository;
    private final ComboMapper comboMapper;
    private final MenuCategoryService menuCategoryService;

    @Override
    public ComboResponse getCombo(Long id) {
        return comboMapper.toResponse(findById(id));
    }

    @Transactional
    @Override
    public ComboResponse createCombo(ComboRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        Combo combo = comboMapper.toEntity(request);

        if (request.getMenuCategoryId() != null){
            MenuCategory menuCategory = menuCategoryService.findById(request.getMenuCategoryId());
            combo.setMenuCategory(menuCategory);
        }


        return comboMapper.toResponse(comboRepository.save(combo));
    }

    @Transactional
    @Override
    public ComboResponse updateCombo(Long id, ComboRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Combo combo = findById(id);
        comboMapper.update(request, combo);

        if (request.getMenuCategoryId() != null){
            MenuCategory menuCategory = menuCategoryService.findById(request.getMenuCategoryId());
            combo.setMenuCategory(menuCategory);
        }

        return comboMapper.toResponse(comboRepository.save(combo));
    }

    @Transactional
    @Override
    public void deleteCombo(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Combo combo = findById(id);
        comboRepository.delete(combo);
    }

    @Override
    public Combo findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return comboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo not found with id: " + id));
    }

    @Override
    public PageResponse<ComboResponse> getCombos(int page, int size, String sort, String direction) {
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Combo> comboPage = comboRepository.findAll(pageable);

        return PageResponse.<ComboResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(comboPage.getTotalPages())
                .totalElements(comboPage.getTotalElements())
                .content(comboPage.getContent().stream()
                        .map(comboMapper::toResponse)
                        .toList())
                .build();
    }

    @Override
    public PageResponse<ComboResponse> searchCombos(String query, String menuCategory, Double minPrice, Double maxPrice,
                                                    int page, int size, String sort, String direction) {

        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
        }
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Combo> comboPage = comboRepository.searchCombos(query, menuCategory, minPrice, maxPrice, pageable);

        return PageResponse.<ComboResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(comboPage.getTotalPages())
                .totalElements(comboPage.getTotalElements())
                .content(comboPage.getContent().stream()
                        .map(comboMapper::toResponse)
                        .toList())
                .build();
    }
}
