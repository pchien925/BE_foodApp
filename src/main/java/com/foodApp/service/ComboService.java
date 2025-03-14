package com.foodApp.service;

import com.foodApp.dto.request.ComboRequest;
import com.foodApp.dto.response.ComboResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.entity.Combo;

public interface ComboService {

    ComboResponse getCombo(Long id);

    ComboResponse createCombo(ComboRequest request);

    ComboResponse updateCombo(Long id, ComboRequest request);

    void deleteCombo(Long id);

    Combo findById(Long id);

    PageResponse<ComboResponse> getCombos(int page, int size, String sort, String direction);

    PageResponse<ComboResponse> searchCombos(String query, String menuCategory, Double minPrice, Double maxPrice,
                                             int page, int size, String sort, String direction);
}
