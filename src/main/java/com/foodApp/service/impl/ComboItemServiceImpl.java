package com.foodApp.service.impl;

import com.foodApp.dto.request.ComboItemRequest;
import com.foodApp.dto.response.ComboItemResponse;
import com.foodApp.entity.Combo;
import com.foodApp.entity.ComboItem;
import com.foodApp.entity.MenuItem;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.ComboItemMapper;
import com.foodApp.repository.ComboItemRepository;
import com.foodApp.service.ComboItemService;
import com.foodApp.service.ComboService;
import com.foodApp.service.MenuItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboItemServiceImpl implements ComboItemService {
    private final ComboItemRepository comboItemRepository;
    private final ComboItemMapper comboItemMapper;
    private final MenuItemService menuItemService;
    private final ComboService comboService;

    @Override
    public ComboItem findById(Long id) {
        return comboItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ComboItem not found with id " + id));
    }

    @Override
    public ComboItemResponse getComboItem(Long id) {
        ComboItem comboItem = findById(id);
        return comboItemMapper.toResponse(comboItem);
    }

    @Override
    @Transactional
    public ComboItemResponse createComboItem(Long comboId, ComboItemRequest request) {
        if (comboId == null || request == null) {
            throw new IllegalArgumentException("Request or combo ID cannot be null");
        }

        ComboItem comboItem = comboItemMapper.toEntity(request);

        if (request.getMenuItemId() != null) {
            MenuItem menuItem = menuItemService.findById(request.getMenuItemId());
            comboItem.setMenuItem(menuItem);
        }

        Combo combo = comboService.findById(comboId);
        comboItem.setCombo(combo);

        return comboItemMapper.toResponse(comboItemRepository.save(comboItem));
    }

    @Override
    @Transactional
    public ComboItemResponse updateComboItem(Long comboId, Long comboItemId, ComboItemRequest request) {
        ComboItem comboItem = comboItemRepository.findById(comboItemId)
                .orElseThrow(() -> new ResourceNotFoundException("ComboItem not found with ID: " + comboItemId));

        if (!comboItem.getCombo().getId().equals(comboId)) {
            throw new IllegalArgumentException("ComboItem with ID " + comboItemId + " does not belong to Combo with ID " + comboId);
        }

        comboItemMapper.update(request, comboItem);

        if (request.getMenuItemId() != null) {
            MenuItem menuItem = menuItemService.findById(request.getMenuItemId());
            comboItem.setMenuItem(menuItem);
        }

        // 5. Lưu và trả về response
        ComboItem updatedComboItem = comboItemRepository.save(comboItem);
        return comboItemMapper.toResponse(updatedComboItem);
    }

    @Override
    @Transactional
    public void deleteComboItem( Long comboId, Long id) {
        Combo combo = comboService.findById(comboId);

        ComboItem comboItem = comboItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ComboItem not found with ID: " + id));

        if (!comboItem.getCombo().getId().equals(comboId)) {
            throw new IllegalArgumentException("ComboItem with ID " + id + " does not belong to Combo with ID " + comboId);
        }

        comboItemRepository.delete(comboItem);
    }

    @Override
    public List<ComboItemResponse> getComboItems(Long comboId) {
        if (comboId == null) {
            throw new IllegalArgumentException("combo ID cannot be null");
        }

        return comboItemRepository.findByCombo_Id(comboId)
                .stream()
                .map(comboItemMapper::toResponse)
                .toList();
    }
}