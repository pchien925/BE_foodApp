package vn.edu.hcmute.foodapp.service;

import vn.edu.hcmute.foodapp.dto.request.AddCartItemRequest;
import vn.edu.hcmute.foodapp.dto.response.CartResponse;

public interface CartService {
    CartResponse getCart(Long userId, String sessionId);

    CartResponse addItemToCart(Long userId, String sessionID, AddCartItemRequest request);
}
