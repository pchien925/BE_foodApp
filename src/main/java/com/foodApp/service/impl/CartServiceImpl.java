package com.foodApp.service.impl;

import com.foodApp.dto.request.CartItemRequest;
import com.foodApp.dto.response.CartResponse;
import com.foodApp.entity.*;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.CartItemMapper;
import com.foodApp.mapper.CartMapper;
import com.foodApp.repository.CartRepository;
import com.foodApp.repository.OptionValueRepository;
import com.foodApp.service.CartService;
import com.foodApp.service.MenuItemService;
import com.foodApp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final UserService userService;
    private final MenuItemService menuItemService;
    private final OptionValueRepository optionValueRepository;

    @Transactional
    @Override
    public Cart findByUsername(String email) {
        return cartRepository.findByUser_Email(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with email: " + email));
    }

    @Override
    @Transactional
    public CartResponse getCart() {
        return cartMapper.toResponse(getOrCreateCart());
    }

    @Transactional
    @Override
    public CartResponse addMenuItemToCart(CartItemRequest request) {
        if (request == null || request.getMenuItemId() == null) {
            throw new IllegalArgumentException("CartItemRequest or menuItemId cannot be null");
        }

        Cart cart = getOrCreateCart();
        MenuItem menuItem = menuItemService.findById(request.getMenuItemId());
        CartItem menuCartItem = cartItemMapper.toEntity(request);
        menuCartItem.setMenuItem(menuItem);
        menuCartItem.setCart(cart);

        if (request.getSelectedOptions() != null && !request.getSelectedOptions().isEmpty()) {
            Set<OptionValue> selectedOptions = optionValueRepository.findByIdInAndOptionType_MenuItems_Id(request.getSelectedOptions(), menuItem.getId());
            if (selectedOptions.size() != request.getSelectedOptions().size()) {
                throw new ResourceNotFoundException("Invalid option values for menu item with ID: " + menuItem.getId());
            }
            validateSingleOptionValuePerOptionType(selectedOptions);
            menuCartItem.setSelectedOptions(selectedOptions);
        }

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(request.getMenuItemId()) &&
                        item.getSelectedOptions().equals(menuCartItem.getSelectedOptions()))
                .findFirst()
                .orElse(null);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            cart.getCartItems().add(menuCartItem);
        }

        log.info("Added/Updated menu item {} in cart for user {}", request.getMenuItemId(), getUsername());
        return cartMapper.toResponse(cartRepository.save(cart));
    }

    @Transactional
    @Override
    public CartResponse removeCartItem(Long cartItemId) {
        if (cartItemId == null) {
            throw new IllegalArgumentException("CartItem ID cannot be null");
        }

        Cart cart = getOrCreateCart();
        CartItem cartItemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with ID: " + cartItemId));

        cart.getCartItems().remove(cartItemToRemove);
        log.info("Removed cart item {} from cart for user {}", cartItemId, getUsername());
        return cartMapper.toResponse(cartRepository.save(cart));
    }

    @Transactional
    @Override
    public CartResponse updateCartItemQuantity(Long cartItemId, Integer quantity) {
        if (cartItemId == null || quantity == null) {
            throw new IllegalArgumentException("CartItem ID and quantity cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Cart cart = getOrCreateCart();
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with ID: " + cartItemId));

        cartItem.setQuantity(quantity);
        log.info("Updated quantity of cart item {} to {} for user {}", cartItemId, quantity, getUsername());
        return cartMapper.toResponse(cartRepository.save(cart));
    }

    @Transactional
    @Override
    public CartResponse clearCart() {
        Cart cart = getOrCreateCart();
        cart.getCartItems().clear();
        log.info("Cleared cart for user {}", getUsername());
        return cartMapper.toResponse(cartRepository.save(cart));
    }

//    @Transactional
//    @Override
//    public OrderResponse checkoutCart(String deliveryName, String deliveryPhone, String deliveryAddress, Long branchId, OrderType orderType) {
//        Cart cart = getOrCreateCart();
//        if (cart.getCartItems().isEmpty()) {
//            throw new IllegalStateException("Cannot checkout an empty cart");
//        }
//
//        Branch branch = branchRepository.findById(branchId)
//                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with ID: " + branchId));
//
//        OrderResponse orderResponse = orderService.createOrderFromCart(cart, deliveryName, deliveryPhone, deliveryAddress, branch, orderType);
//        cart.getCartItems().clear();
//        cartRepository.save(cart);
//        log.info("Checked out cart for user {} and created order {}", getUsername(), orderResponse.getId());
//        return orderResponse;
//    }

    private Cart getOrCreateCart() {
        String username = getUsername();
        return cartRepository.findByUser_Email(username)
                .orElseGet(() -> createNewCart(username));
    }

    private Cart createNewCart(String username) {
        Cart cart = new Cart();
        cart.setUser(userService.findByEmail(username));
        return cartRepository.save(cart);
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new SecurityException("No authenticated user found or invalid authentication");
        }
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    private void validateSingleOptionValuePerOptionType(Set<OptionValue> selectedOptions) {
        Set<Long> optionTypeIds = new HashSet<>();
        for (OptionValue optionValue : selectedOptions) {
            Long optionTypeId = optionValue.getOptionType().getId();
            if (!optionTypeIds.add(optionTypeId)) {
                throw new IllegalArgumentException("Only one OptionValue per OptionType is allowed. Duplicate found for OptionType ID: " + optionTypeId);
            }
        }
    }
}