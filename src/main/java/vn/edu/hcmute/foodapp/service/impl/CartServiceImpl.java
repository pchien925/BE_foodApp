package vn.edu.hcmute.foodapp.service.impl;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.AddCartItemRequest;
import vn.edu.hcmute.foodapp.dto.request.UpdateCartItemQuantityRequest;
import vn.edu.hcmute.foodapp.dto.response.CartResponse;
import vn.edu.hcmute.foodapp.entity.*;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.CartMapper;
import vn.edu.hcmute.foodapp.repository.*;
import vn.edu.hcmute.foodapp.service.CartService;
import vn.edu.hcmute.foodapp.util.SecurityUtil;

import java.math.BigDecimal;
import java.util.*;
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
    @Transactional
    public CartResponse getCart(String sessionId){
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("Fetching cart for userId: {} and sessionId: {}", userId, sessionId);
        Cart cart = findOrCreateCart(userId, sessionId);
        if (cart.getCartItems() == null) {
            cart.setCartItems(new HashSet<>());
        }
        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addItemToCart(String sessionId, AddCartItemRequest request) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("Adding item to cart for userId: {} and sessionId: {}", userId, sessionId);
        Cart cart = findOrCreateCart(userId, sessionId);

        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));

        List<MenuItemOption> menuItemOptions = validateAndGetSelectedOptions(request.getSelectedMenuItemOptionIds(), menuItem);
        BigDecimal priceAtAddition = calculatePriceAtAddition(menuItem, menuItemOptions);

        Optional<CartItem> existingCartItem = findExistingCartItem(cart, menuItem, menuItemOptions);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .menuItem(menuItem)
                    .quantity(request.getQuantity())
                    .priceAtAddition(priceAtAddition)
                    .cartItemOptions(new HashSet<>())
                    .build();
            cart.getCartItems().add(cartItem);
            CartItem savedCartItem = cartItemRepository.save(cartItem);

            List<CartItemOption> cartItemOptions = menuItemOptions.stream()
                    .map(option -> CartItemOption.builder()
                            .cartItem(savedCartItem)
                            .menuItemOption(option)
                            .build())
                    .toList();
            savedCartItem.getCartItemOptions().addAll(cartItemOptions);
            cartItemOptionRepository.saveAll(cartItemOptions);
        }

        Cart updatedCart = cartRepository.findById(cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found after clearing"));
        return buildCartResponse(updatedCart);
    }

    @Override
    @Transactional
    public CartResponse updateItemQuantity(String sessionId, Long cartItemId, UpdateCartItemQuantityRequest request) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("Updating item quantity in cart for userId: {} and sessionId: {}", userId, sessionId);
        if (request.getQuantity() <= 0) {
            return removeItemFromCart(sessionId, cartItemId);
        }
        Cart cart = findCartByUserIdOrSessionId(userId, sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartItem cartItem = cartItemRepository.findByCart_IdAndId(cart.getId(), cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        return buildCartResponse(cartRepository.findById(cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found")));
    }

    @Override
    @Transactional
    public CartResponse removeItemFromCart(String sessionId, Long cartItemId) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("Removing item from cart for userId: {} and sessionId: {}", userId, sessionId);
        Cart cart = findCartByUserIdOrSessionId(userId, sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository.findByCart_IdAndId(cart.getId(), cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));

        cart.getCartItems().remove(cartItem);
        return buildCartResponse(cartRepository.save(cart));
    }


    @Transactional
    @Override
    public CartResponse clearCart(String sessionId) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("Clearing cart for userId: {} and sessionId: {}", userId, sessionId);
        Cart cart = findCartByUserIdOrSessionId(userId, sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getCartItems().clear();
        cartRepository.save(cart);
        return buildCartResponse(cart);
    }


    private Cart findOrCreateCart(Long userId, String sessionId){
        log.info("Finding or creating cart for userId: {} and sessionId: {}", userId, sessionId);
        if (userId == null && sessionId == null) {
            throw new IllegalArgumentException("Either userId or sessionId must be provided");
        }
        if (!cartRepository.existsByUser_IdOrSessionId(userId, sessionId)) {
            return createNewCart(userId, sessionId);
        }
        return findCartByUserIdOrSessionId(userId, sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
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

        List<MenuItemOption> menuItemOptions = menuItemOptionRepository.findAllByIdsAndMenuItem_Id(selectedMenuItemOptionIds, menuItem.getId());
        if (menuItemOptions.size() != selectedMenuItemOptionIds.size()) {
            Set<Integer> foundIds = menuItemOptions.stream().map(MenuItemOption::getId).collect(Collectors.toSet());
            List<Integer> missingOrInvalidIds = selectedMenuItemOptionIds.stream().filter(id -> !foundIds.contains(id)).toList();
            throw new ResourceNotFoundException("Invalid or non-existent MenuItemOption IDs for this MenuItem: " + missingOrInvalidIds);
        }

        Map<Option, String> optionMap = new HashMap<>();
        for (MenuItemOption option : menuItemOptions) {
            if (option.getOption() == null) {
                log.warn("MenuItemOption ID {} has null Option entity.", option.getId());
                throw new IllegalStateException("Data inconsistency: MenuItemOption ID " + option.getId() + " is missing its Option relation.");
            }
            if (optionMap.containsKey(option.getOption())) {
                throw new IllegalArgumentException("Duplicate option type selected for: " + option.getOption().getName());
            }
            optionMap.put(option.getOption(), option.getValue());
        }

        return menuItemOptions;
    }


    private Optional<Cart> findCartByUserIdOrSessionId(Long userId, String sessionId) {
        if (userId != null) {
            return cartRepository.findFullCartByUser_Id(userId);
        } else if (sessionId != null && !sessionId.isBlank()) {
            return cartRepository.findByFullCartSessionId(sessionId);
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

    private BigDecimal calculateTotalPrice(Cart cart) {
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return cart.getCartItems().stream()
                .map(cartItem -> cartItem.getPriceAtAddition()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //Build response với totalPrice
    private CartResponse buildCartResponse(Cart cart) {
        CartResponse response = CartMapper.INSTANCE.toResponse(cart);
        response.setTotalPrice(calculateTotalPrice(cart));
        return response;
    }
}
