package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.CreateOrderRequest;
import vn.edu.hcmute.foodapp.dto.response.*;
import vn.edu.hcmute.foodapp.entity.*;
import vn.edu.hcmute.foodapp.exception.InvalidDataException; // Nên dùng
import vn.edu.hcmute.foodapp.exception.OrderCreationFailedException;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.OrderMapper;
import vn.edu.hcmute.foodapp.repository.*;
import vn.edu.hcmute.foodapp.service.OrderService;
import vn.edu.hcmute.foodapp.util.PaginationUtil;
import vn.edu.hcmute.foodapp.util.enumeration.EOrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemOptionRepository orderItemOptionRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final CartRepository cartRepository;
    private final MenuItemRepository menuItemRepository;
    private final MenuItemOptionRepository menuItemOptionRepository;
    private final CartItemOptionRepository cartItemOptionRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public OrderInfoResponse createOrder(Long userId, CreateOrderRequest request){
        log.info("Starting order creation process for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Branch branch = branchRepository.findById(request.getBranchId())
                .filter(Branch::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Active branch not found with id: " + request.getBranchId()));

        Cart cart = cartRepository.findFullCartByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        Set<CartItem> cartItems = cart.getCartItems();
        // Sửa: Dùng InvalidDataException cho ngữ nghĩa tốt hơn
        if (cartItems == null || cartItems.isEmpty()) {
            throw new InvalidDataException("Cannot create order from an empty cart.");
        }
        log.debug("Cart found for user {} with {} items.", userId, cartItems.size());

        // Tạo Order ban đầu
        Order order = OrderMapper.INSTANCE.toEntity(request);
        order.setUser(user);
        order.setBranch(branch);
        order.setOrderCode(generateOrderCode());
        order.setOrderStatus(EOrderStatus.PENDING);
        order.setTotalPrice(BigDecimal.ZERO);

        order = orderRepository.save(order);
        log.info("Initial Order entity created with ID: {}", order.getId());

        BigDecimal finalOrderTotalPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            log.debug("Processing CartItem ID: {}", cartItem.getId());
            MenuItem menuItem = menuItemRepository.findById(cartItem.getMenuItem().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item referenced in cart not found (ID: " + cartItem.getMenuItem().getId() + ")"));

            if (!menuItem.getIsAvailable()) {
                log.warn("Item '{}' (ID: {}) is no longer available. Skipping item.", menuItem.getName(), menuItem.getId());
                throw new OrderCreationFailedException("Item '" + menuItem.getName() + "' is no longer available.");
            }

            BigDecimal itemBasePrice = cartItem.getPriceAtAddition();
            if (itemBasePrice == null) {
                log.error("CartItem ID {} for user {} is missing priceAtAddition!", cartItem.getId(), userId);
                throw new IllegalStateException("Cart item data is inconsistent. Missing price information for item " + menuItem.getName());
            }


            // Tạo OrderItem ban đầu
            OrderItem orderItem = OrderItem.builder()
                    .menuItem(menuItem)
                    .order(order)
                    .quantity(cartItem.getQuantity())
                    .pricePerUnit(itemBasePrice)
                    .build();

            // Tính giá cơ bản của item (chưa có option)
            BigDecimal orderItemBaseTotal = itemBasePrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            orderItem.setTotalPrice(orderItemBaseTotal); // Set giá ban đầu

            // Lưu OrderItem trước khi xử lý option (để có ID)
            orderItem = orderItemRepository.save(orderItem);
            log.debug("Saved OrderItem ID: {} for MenuItem ID: {}. Base total: {}", orderItem.getId(), menuItem.getId(), orderItemBaseTotal);

            // Xử lý Options
            BigDecimal itemOptionsTotal = BigDecimal.ZERO;
            Set<CartItemOption> cartItemOptions = cartItem.getCartItemOptions();

            // Chỉ xử lý nếu có option
            if (cartItemOptions != null && !cartItemOptions.isEmpty()) {
                log.debug("Processing {} options for CartItem ID: {}", cartItemOptions.size(), cartItem.getId());
                for (CartItemOption cartItemOption : cartItemOptions) {
                    // Lấy MenuItemOption tương ứng
                    MenuItemOption menuItemOption = menuItemOptionRepository.findById(cartItemOption.getMenuItemOption().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Menu item option referenced in cart not found (ID: " + cartItemOption.getMenuItemOption().getId() + ")"));

                    BigDecimal originalOptionPrice = menuItemOption.getAdditionalPrice();
                    BigDecimal effectiveOptionPrice = Objects.requireNonNullElse(originalOptionPrice, BigDecimal.ZERO);
                    if (originalOptionPrice == null) {
                        log.warn("MenuItemOption ID {} ('{}' - '{}') has null additionalPrice. Treating as BigDecimal.ZERO.",
                                menuItemOption.getId(),
                                menuItemOption.getOption().getName(),
                                menuItemOption.getValue());
                    }

                    // Tạo OrderItemOption
                    OrderItemOption orderItemOption = OrderItemOption.builder()
                            .orderItem(orderItem)
                            .optionName(menuItemOption.getOption().getName())
                            .optionValue(menuItemOption.getValue())
                            .additionalPrice(effectiveOptionPrice)
                            .build();

                    orderItemOptionRepository.save(orderItemOption);

                    itemOptionsTotal = itemOptionsTotal.add(effectiveOptionPrice);
                }


                orderItem.setTotalPrice(orderItemBaseTotal.add(itemOptionsTotal));
                orderItem = orderItemRepository.save(orderItem);
                log.debug("Updated OrderItem ID: {} total price including options: {}", orderItem.getId(), orderItem.getTotalPrice());
            }
            finalOrderTotalPrice = finalOrderTotalPrice.add(orderItem.getTotalPrice());
            log.debug("Current finalOrderTotalPrice after adding Item ID {}: {}", orderItem.getId(), finalOrderTotalPrice);
        }

        order.setTotalPrice(finalOrderTotalPrice);
        order = orderRepository.save(order);
        log.info("Final Order ID: {} updated with total price: {}", order.getId(), finalOrderTotalPrice);

        clearCartItemsAfterOrder(cart, userId, order.getId());

        OrderInfoResponse response = OrderMapper.INSTANCE.toInfoResponse(order);
        log.info("Order creation process completed successfully for Order ID: {}", order.getId());
        return response;
    }


    @Transactional(readOnly = true)
    @Override
    public PageResponse<OrderSummaryResponse> getUserOrders(Long userId, int page, int size, String sort, String direction, EOrderStatus statusFilter){
        log.info("Retrieving orders for user ID: {} with status filter: {}", userId, statusFilter);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Order> orderPage = orderRepository.findByUser_IdAndOrderStatus(userId, statusFilter, pageable);

        return PageResponse.<OrderSummaryResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(orderPage.getTotalPages())
                .totalElements(orderPage.getTotalElements())
                .content(orderPage.getContent().stream()
                        .map(OrderMapper.INSTANCE::toSummaryResponse)
                        .toList())
                .build();

    }

    @Transactional(readOnly = true)
    @Override
    public OrderDetailsResponse getOrderDetailForUser(Long userId, Long orderId) {
        log.debug("Fetching order detail for order ID: {} for user ID: {}", orderId, userId);
        Order order = orderRepository.findOrderWithDetailsById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        // Kiểm tra quyền truy cập của user
        if (!order.getUser().getId().equals(userId)) {
            log.warn("Access denied for user ID: {} trying to access order ID: {}", userId, orderId);
            throw new AccessDeniedException("You do not have permission to view this order.");
        }

        log.info("Order detail found for order ID: {} for user ID: {}", orderId, userId);
        return OrderMapper.INSTANCE.toDetailsResponse(order);
    }


    // Hàm helper để xóa giỏ hàng một cách an toàn
    private void clearCartItemsAfterOrder(Cart cart, Long userId, Long orderId) {
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return;
        }
        try {
            log.debug("Attempting to clear cart for user ID: {} after creating order ID: {}", userId, orderId);
            List<Long> cartItemOptionIdsToDelete = new ArrayList<>();
            List<Long> cartItemIdsToDelete = new ArrayList<>();

            // Thu thập ID cần xóa
            for (CartItem cartItem : cart.getCartItems()) {
                cartItemIdsToDelete.add(cartItem.getId());
                if (cartItem.getCartItemOptions() != null) {
                    cartItem.getCartItemOptions().forEach(cio -> cartItemOptionIdsToDelete.add(cio.getId()));
                }
            }

            // Thực hiện xóa (Option trước)
            if (!cartItemOptionIdsToDelete.isEmpty()) {
                cartItemOptionRepository.deleteAllByIdInBatch(cartItemOptionIdsToDelete);
                log.debug("Deleted {} cart item options.", cartItemOptionIdsToDelete.size());
            }
            if (!cartItemIdsToDelete.isEmpty()) {
                cartItemRepository.deleteAllByIdInBatch(cartItemIdsToDelete);
                log.debug("Deleted {} cart items.", cartItemIdsToDelete.size());
            }
            log.info("Cleared cart for user ID: {} after successful order creation (Order ID: {}).", userId, orderId);
        } catch (Exception e) {
            // Log lỗi nhưng không rollback transaction của đơn hàng
            log.error("Error clearing cart for user ID: {} after creating order ID: {}. Error: {}", userId, orderId, e.getMessage(), e);
        }
    }

    private String generateOrderCode() {
        long timestamp = Instant.now().toEpochMilli();
        int randomNum = (int) (Math.random() * 10000);
        String code = String.valueOf(timestamp) + String.format("%04d", randomNum);
        String numericPart = code.substring(Math.max(0, code.length() - 10));
        return "ORDER-" + numericPart;
    }
}