package vn.edu.hcmute.foodapp.service;

import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.AddCartItemRequest;
import vn.edu.hcmute.foodapp.dto.request.UpdateCartItemQuantityRequest;
import vn.edu.hcmute.foodapp.dto.response.CartResponse;

public interface CartService {
    CartResponse getCart(Long userId, String sessionId);

    CartResponse addItemToCart(Long userId, String sessionID, AddCartItemRequest request);

    CartResponse updateItemQuantity(Long userId, String sessionId, Long cartItemId, UpdateCartItemQuantityRequest request);

    CartResponse removeItemFromCart(Long userId, String sessionId, Long cartItemId);

    CartResponse clearCart(Long userId, String sessionId);
}
