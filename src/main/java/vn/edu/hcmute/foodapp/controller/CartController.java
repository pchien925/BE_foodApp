package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.AddCartItemRequest;
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
        CartResponse response = cartService.getCart(userId, sessionId);
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cart details retrieved successfully")
                .data(response)
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
                .data(cartService.addItemToCart(userId, sessionId, request))
                .build();
    }
}
