package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.AddCartItemRequest;
import vn.edu.hcmute.foodapp.dto.response.CartResponse;
import vn.edu.hcmute.foodapp.entity.*;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.CartMapper;
import vn.edu.hcmute.foodapp.repository.*;
import vn.edu.hcmute.foodapp.service.CartService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemOptionRepository cartItemOptionRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final MenuItemOptionRepository menuItemOptionRepository;

    @Override
    public CartResponse getCart(Long userId, String sessionId){
        log.info("Fetching cart for userId: {} and sessionId: {}", userId, sessionId);
        Cart cart = findOrCreateCart(userId, sessionId);
        if (cart.getCartItems() == null) {
            cart.setCartItems(new HashSet<>());
        }
        return CartMapper.INSTANCE.toResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addItemToCart(Long userId, String sessionId, AddCartItemRequest request){
        log.info("Adding item to cart for userId: {} and sessionId: {}", userId, sessionId);
        Cart cart = findOrCreateCart(userId, sessionId);

        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));

        // kiểm tra menu item option có đúng của menu item không
        List<MenuItemOption> menuItemOptions =
                validateAndGetSelectedOptions(request.getSelectedMenuItemOptionIds(), menuItem);

        // tính giá trị priceAtAddition
        BigDecimal priceAtAddition = calculatePriceAtAddition(menuItem, menuItemOptions);

        // kiểm tra xem menu item option đã có trong cart chưa
        Optional<CartItem> existingCartItem = findExistingCartItem(cart, menuItem, menuItemOptions);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(cartItem);
        }
        else {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .menuItem(menuItem)
                    .quantity(request.getQuantity())
                    .priceAtAddition(priceAtAddition)
                    .build();
            CartItem savedCartItem = cartItemRepository.save(cartItem);

            List<CartItemOption> cartItemOptions = menuItemOptions.stream()
                    .map(option -> CartItemOption.builder()
                            .cartItem(savedCartItem)
                            .menuItemOption(option)
                            .build())
                    .toList();
            cartItemOptionRepository.saveAll(cartItemOptions);
        }

        Cart updatedCart = cartRepository.findById(cart.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found")
        );

        return CartMapper.INSTANCE.toResponse(updatedCart);
    }




    private Cart findOrCreateCart(Long userId, String sessionId){
        log.info("Finding or creating cart for userId: {} and sessionId: {}", userId, sessionId);
        Optional<Cart> cartOptional = findCartByUserIdOrSessionId(userId, sessionId);
        return cartOptional.orElseGet(() -> createNewCart(userId, sessionId));
    }

    private Cart createNewCart(Long userId, String sessionId) {
        log.info("Creating new cart for userId: {} and sessionId: {}", userId, sessionId);
        Cart newCart = new Cart();
        if (userId != null) {
            newCart.setUser(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found")));
        }
        else if (sessionId != null) {
            newCart.setSessionId(sessionId);
        } else {
            throw new IllegalArgumentException("Either userId or sessionId must be provided");
        }
        newCart.setCartItems(new HashSet<>());
        return cartRepository.save(newCart);
    }

    private List<MenuItemOption> validateAndGetSelectedOptions(List<Integer> selectedMenuItemOptionIds, MenuItem menuItem) {
        if (selectedMenuItemOptionIds == null || selectedMenuItemOptionIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<MenuItemOption> menuItemOptions = menuItemOptionRepository.findAllById(selectedMenuItemOptionIds);

        if (menuItemOptions.size() != selectedMenuItemOptionIds.size()) {
            throw new ResourceNotFoundException("Some menu item options not found");
        }

        for (MenuItemOption option : menuItemOptions) {
            if (!menuItem.getMenuItemOptions().contains(option)) {
                throw new ResourceNotFoundException("Menu item option not valid for this menu item");
            }
        }

        return menuItemOptions;
    }


    private Optional<Cart> findCartByUserIdOrSessionId(Long userId, String sessionId) {
        if (userId != null) {
            return cartRepository.findByUser_Id(userId);
        } else if (sessionId != null && !sessionId.isBlank()) {
            return cartRepository.findBySessionId(sessionId);
        }
        return Optional.empty();
    }

    private Optional<CartItem> findExistingCartItem(Cart cart, MenuItem menuItem, List<MenuItemOption> menuItemOptions) {
        if (cart == null || cart.getCartItems() == null || menuItem == null || menuItemOptions == null) {
            return Optional.empty();
        }

        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getMenuItem().equals(menuItem) &&
                        new HashSet<>(cartItem.getCartItemOptions().stream()
                                .map(CartItemOption::getMenuItemOption)
                                .toList())
                                .equals(new HashSet<>(menuItemOptions)))
                .findFirst();
    }

    private BigDecimal calculatePriceAtAddition(MenuItem menuItem, List<MenuItemOption> menuItemOptions) {
        BigDecimal totalPrice = menuItem.getBasePrice();
        for (MenuItemOption option : menuItemOptions) {
            totalPrice = totalPrice.add(option.getAdditionalPrice());
        }
        return totalPrice;
    }
}
