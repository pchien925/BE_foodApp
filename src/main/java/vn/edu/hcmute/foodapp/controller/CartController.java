package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.AddCartItemRequest;
import vn.edu.hcmute.foodapp.dto.request.UpdateCartItemQuantityRequest;
import vn.edu.hcmute.foodapp.dto.response.CartResponse;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.CartService;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "CART", description = "Cart API")
@Slf4j(topic = "CART_CONTROLLER")
public class CartController {
    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get cart details", description = "Retrieve the details of the user's cart.")
    public ResponseData<CartResponse> getCartDetails(@RequestParam(required = false) Long userId,
                                                     @RequestParam(required = false) String sessionId) {
        log.info("Fetching cart details");
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cart details retrieved successfully")
                .data(cartService.getCart(sessionId))
                .build();
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart", description = "Add a new item to the user's cart.")
    public ResponseData<CartResponse> addItemToCart(@RequestParam(required = false) Long userId,
                                                    @RequestParam(required = false) String sessionId,
                                                    @RequestBody AddCartItemRequest request) {
        log.info("Adding item to cart");
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Item added to cart successfully")
                .data(cartService.addItemToCart( sessionId, request))
                .build();
    }

    @PatchMapping("/items/{cartItemId}")
    @Operation(summary = "Update item quantity", description = "Update the quantity of an item in the user's cart.")
    public ResponseData<CartResponse> updateItemQuantity(@RequestParam(required = false) Long userId,
                                                          @RequestParam(required = false) String sessionId,
                                                          @PathVariable Long cartItemId,
                                                          @RequestBody UpdateCartItemQuantityRequest request) {
        log.info("Updating item quantity in cart");
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Item quantity updated successfully")
                .data(cartService.updateItemQuantity(sessionId, cartItemId, request))
                .build();
    }

    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Remove item from cart", description = "Remove an item from the user's cart.")
    public ResponseData<CartResponse> removeItemFromCart(@RequestParam(required = false) Long userId,
                                                          @RequestParam(required = false) String sessionId,
                                                          @PathVariable Long cartItemId) {
        log.info("Removing item from cart");
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Item removed from cart successfully")
                .data(cartService.removeItemFromCart(sessionId, cartItemId))
                .build();
    }

    @DeleteMapping
    @Operation(summary = "Clear cart", description = "Clear all items from the user's cart.")
    public ResponseData<CartResponse> clearCart(@RequestParam(required = false) Long userId,
                                                @RequestParam(required = false) String sessionId) {
        log.info("Clearing cart");
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cart cleared successfully")
                .data(cartService.clearCart( sessionId))
                .build();
    }
}
