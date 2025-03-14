package com.foodApp.service;

import com.foodApp.dto.request.ComboItemRequest;
import com.foodApp.dto.response.ComboItemResponse;
import com.foodApp.entity.ComboItem;

import java.util.List;

public interface ComboItemService {
    ComboItem findById(Long id);

    ComboItemResponse getComboItem(Long id);

    ComboItemResponse createComboItem(Long comboId, ComboItemRequest request);

    ComboItemResponse updateComboItem(Long comboId, Long comboItemId, ComboItemRequest request);

    void deleteComboItem(Long comboId, Long id);

    List<ComboItemResponse> getComboItems(Long comboId);
}
