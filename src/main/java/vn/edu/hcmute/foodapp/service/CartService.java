package vn.edu.hcmute.foodapp.service;

import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.AddCartItemRequest;
import vn.edu.hcmute.foodapp.dto.request.UpdateCartItemQuantityRequest;
import vn.edu.hcmute.foodapp.dto.response.CartResponse;

public interface CartService {
    CartResponse getCart(String sessionId);

    CartResponse addItemToCart(String sessionID, AddCartItemRequest request);

    CartResponse updateItemQuantity(String sessionId, Long cartItemId, UpdateCartItemQuantityRequest request);

    CartResponse removeItemFromCart(String sessionId, Long cartItemId);

    CartResponse clearCart(String sessionId);
}
