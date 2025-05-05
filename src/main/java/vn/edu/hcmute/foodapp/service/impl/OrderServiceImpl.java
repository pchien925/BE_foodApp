package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.CreateOrderRequest;
import vn.edu.hcmute.foodapp.dto.request.NotificationRequest;
import vn.edu.hcmute.foodapp.dto.request.UpdateOrderStatusRequest;
import vn.edu.hcmute.foodapp.dto.response.*;
import vn.edu.hcmute.foodapp.entity.*;
import vn.edu.hcmute.foodapp.exception.InvalidActionException;
import vn.edu.hcmute.foodapp.exception.InvalidDataException;
import vn.edu.hcmute.foodapp.exception.OrderCreationFailedException;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.OrderMapper;
import vn.edu.hcmute.foodapp.repository.*;
import vn.edu.hcmute.foodapp.service.NotificationService;
import vn.edu.hcmute.foodapp.service.OrderService;
import vn.edu.hcmute.foodapp.util.PaginationUtil;
import vn.edu.hcmute.foodapp.util.enumeration.ELoyaltyTransactionType;
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
    private final NotificationService notificationService;
    private final LoyaltyPointTransactionRepository loyaltyPointTransactionRepository;

    @Value("${loyalty.point-rate}")
    private BigDecimal POINT_RATE;

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
        if (cartItems == null || cartItems.isEmpty()) {
            throw new InvalidDataException("Cannot create order from an empty cart.");
        }
        log.debug("Cart found for user {} with {} items.", userId, cartItems.size());

        // Tạo Order ban đầu
        Order order = OrderMapper.INSTANCE.toEntity(request);
        order.setUser(user);
        order.setBranch(branch);
        order.setOrderCode(generateOrderCode());
        order.setOrderStatus(EOrderStatus.PROCESSING);
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
            orderItem.setTotalPrice(orderItemBaseTotal);

            // Lưu OrderItem trước khi xử lý option
            orderItem = orderItemRepository.save(orderItem);
            log.debug("Saved OrderItem ID: {} for MenuItem ID: {}. Base total: {}", orderItem.getId(), menuItem.getId(), orderItemBaseTotal);

            // Xử lý Options
            BigDecimal itemOptionsTotal = BigDecimal.ZERO;
            Set<CartItemOption> cartItemOptions = cartItem.getCartItemOptions();

            if (cartItemOptions != null && !cartItemOptions.isEmpty()) {
                log.debug("Processing {} options for CartItem ID: {}", cartItemOptions.size(), cartItem.getId());
                for (CartItemOption cartItemOption : cartItemOptions) {
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
    public PageResponse<OrderInfoResponse> getUserOrders(Long userId, int page, int size, String sort, String direction, EOrderStatus statusFilter){
        log.info("Retrieving orders for user ID: {} with status filter: {}", userId, statusFilter);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Order> orderPage = orderRepository.findByUser_IdAndOrderStatus(userId, statusFilter, pageable);

        return PageResponse.<OrderInfoResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(orderPage.getTotalPages())
                .totalElements(orderPage.getTotalElements())
                .content(orderPage.getContent().stream()
                        .map(OrderMapper.INSTANCE::toInfoResponse)
                        .toList())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDetailsResponse getOrderDetailForUser(Long userId, Long orderId) {
        log.debug("Fetching order detail for order ID: {} for user ID: {}", orderId, userId);
        Order order = orderRepository.findOrderWithDetailsById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (!order.getUser().getId().equals(userId)) {
            log.warn("Access denied for user ID: {} trying to access order ID: {}", userId, orderId);
            throw new AccessDeniedException("You do not have permission to view this order.");
        }

        log.info("Order detail found for order ID: {} for user ID: {}", orderId, userId);
        return OrderMapper.INSTANCE.toDetailsResponse(order);
    }

    @Override
    @Transactional
    public OrderInfoResponse cancelOrder(Long userId, Long orderId) {
        log.info("User ID: {} attempting to cancel order ID: {}", userId, orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (!order.getUser().getId().equals(userId)) {
            log.warn("Access denied for user ID: {} trying to cancel order ID: {}", userId, orderId);
            throw new AccessDeniedException("You do not have permission to cancel this order.");
        }

        if (order.getOrderStatus() != EOrderStatus.PROCESSING) {
            log.warn("Order ID: {} cannot be cancelled because its status is {}", orderId, order.getOrderStatus());
            throw new InvalidActionException("Order cannot be cancelled in its current state: " + order.getOrderStatus());
        }

        order.setOrderStatus(EOrderStatus.CANCELLED);

        NotificationRequest notification = new NotificationRequest();
        notification.setTitle("Order Cancellation");
        notification.setContent("Your order with order code: " + order.getOrderCode() + " has been cancelled.");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        notificationService.sendAndSaveNotification(notification, user.getEmail());

        Order updatedOrder = orderRepository.save(order);
        log.info("Order ID: {} cancelled successfully by user ID: {}", orderId, userId);
        return OrderMapper.INSTANCE.toInfoResponse(updatedOrder);
    }

    @Transactional
    @Override
    public OrderInfoResponse confirmOrder(Long userId, Long orderId) {
        log.info("User ID: {} attempting to confirm order ID: {}", userId, orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));


        if (order.getOrderStatus() != EOrderStatus.PROCESSING) {
            log.warn("Order ID: {} cannot be confirmed because its status is {}", orderId, order.getOrderStatus());
            throw new InvalidActionException("Order cannot be confirmed in its current state: " + order.getOrderStatus());
        }

        order.setOrderStatus(EOrderStatus.SHIPPING);

        NotificationRequest notification = new NotificationRequest();
        notification.setTitle("Order Confirmation");
        notification.setContent("Your order with order code: " + order.getOrderCode() + " has been confirmed.");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        notificationService.sendAndSaveNotification(notification, user.getEmail());

        Order updatedOrder = orderRepository.save(order);
        log.info("Order ID: {} confirmed successfully by user ID: {}", orderId, userId);
        return OrderMapper.INSTANCE.toInfoResponse(updatedOrder);
    }

    @Transactional
    @Override
    public OrderInfoResponse completeOrder(Long userId, Long orderId) {
        log.info("User ID: {} attempting to complete order ID: {}", userId, orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (order.getOrderStatus() != EOrderStatus.SHIPPING) {
            log.warn("Order ID: {} cannot be completed because its status is {}", orderId, order.getOrderStatus());
            throw new InvalidActionException("Order cannot be completed in its current state: " + order.getOrderStatus());
        }

        order.setOrderStatus(EOrderStatus.COMPLETED);

        addLoyaltyPointsForOrder(order);

        NotificationRequest notification = new NotificationRequest();
        notification.setTitle("Order Completion");
        notification.setContent("Your order with order code: " + order.getOrderCode() + " has been completed.");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        notificationService.sendAndSaveNotification(notification, user.getEmail());

        Order updatedOrder = orderRepository.save(order);
        log.info("Order ID: {} completed successfully by user ID: {}", orderId, userId);
        return OrderMapper.INSTANCE.toInfoResponse(updatedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrderInfoResponse> getAllOrdersAdmin(int page, int size, String sort, String direction,
                                                             EOrderStatus statusFilter, Long userIdFilter,
                                                             Integer branchIdFilter, String orderCodeFilter) {
        log.info("Admin request: Get all orders with filters - Status: {}, UserID: {}, BranchID: {}, Code: {}",
                statusFilter, userIdFilter, branchIdFilter, orderCodeFilter);

        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Order> orderPage = orderRepository.findOrdersAdminWithFilters(
                statusFilter,
                userIdFilter,
                branchIdFilter,
                (orderCodeFilter != null && orderCodeFilter.isBlank()) ? null : orderCodeFilter,
                pageable
        );

        return PageResponse.<OrderInfoResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(orderPage.getTotalPages())
                .totalElements(orderPage.getTotalElements())
                .content(orderPage.getContent().stream()
                        .map(OrderMapper.INSTANCE::toInfoResponse)
                        .toList())
                .build();
    }

    @Override
    @Transactional
    public OrderInfoResponse updateOrderStatusAdmin(Long orderId, UpdateOrderStatusRequest request) {
        log.info("Admin request: Update status for order ID: {} to {}", orderId, request.getStatus());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        EOrderStatus oldStatus = order.getOrderStatus();
        EOrderStatus newStatus = request.getStatus();

        if (!isValidStatusTransition(oldStatus, newStatus)) {
            log.warn("Invalid status transition attempt for order ID: {} from {} to {}", orderId, oldStatus, newStatus);
            throw new InvalidActionException("Cannot change order status from " + oldStatus + " to " + newStatus);
        }

        order.setOrderStatus(newStatus);

        triggerActionsForStatus(order, oldStatus, newStatus, request.getReason());

        Order updatedOrder = orderRepository.save(order);
        log.info("Admin successfully updated status for order ID: {} from {} to {}", orderId, oldStatus, newStatus);
        return OrderMapper.INSTANCE.toInfoResponse(updatedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailsResponse getOrderDetailsAdmin(Long orderId) {
        log.info("Admin request: Get details for order ID: {}", orderId);
        Order order = orderRepository.findOrderWithDetailsById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        log.info("Admin retrieved details for order ID: {}", orderId);
        return OrderMapper.INSTANCE.toDetailsResponse(order);
    }

    private boolean isValidStatusTransition(EOrderStatus oldStatus, EOrderStatus newStatus) {
        if (oldStatus == newStatus) return true;

        if (oldStatus == EOrderStatus.PROCESSING) {
            return newStatus == EOrderStatus.SHIPPING || newStatus == EOrderStatus.CANCELLED;
        }
        if (oldStatus == EOrderStatus.SHIPPING) {
            return newStatus == EOrderStatus.COMPLETED || newStatus == EOrderStatus.CANCELLED;
        }
        if (oldStatus == EOrderStatus.COMPLETED || oldStatus == EOrderStatus.CANCELLED) {
            return false;
        }
        return true;
    }

    private void triggerActionsForStatus(Order order, EOrderStatus oldStatus, EOrderStatus newStatus, String reason) {
        if (oldStatus == newStatus) return;

        if (newStatus == EOrderStatus.COMPLETED) {
            log.debug("Order ID: {} completed. Triggering post-completion actions.", order.getId());
            // Tích điểm thưởng khi đơn hàng hoàn tất
            addLoyaltyPointsForOrder(order);
        }
        else if (newStatus == EOrderStatus.CANCELLED) {
            log.debug("Order ID: {} cancelled. Triggering cancellation actions. Reason: {}", order.getId(), reason);
        }
        else if (newStatus == EOrderStatus.SHIPPING && oldStatus == EOrderStatus.PROCESSING) {
            log.debug("Order ID: {} is now SHIPPING. Triggering preparation actions.", order.getId());
        }
    }

    private void addLoyaltyPointsForOrder(Order order) {
        log.debug("Calculating loyalty points for Order ID: {}", order.getId());
        User user = order.getUser();
        BigDecimal totalPrice = order.getTotalPrice();

        if (totalPrice == null || totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Order ID: {} has invalid total price: {}. Skipping loyalty points.", order.getId(), totalPrice);
            // Gửi thông báo hoàn thành đơn hàng mà không có điểm thưởng
            NotificationRequest notification = new NotificationRequest();
            notification.setTitle("Order Completed");
            notification.setContent("Your order with order code: " + order.getOrderCode() + " has been completed.");
            notificationService.sendAndSaveNotification(notification, user.getEmail());
            return;
        }

        // Tính số điểm thưởng: 1 điểm cho mỗi 100.000 VNĐ
        BigDecimal pointsEarnedBigDecimal = totalPrice.divide(POINT_RATE, 0, BigDecimal.ROUND_DOWN);
        int pointsEarned = pointsEarnedBigDecimal.intValue();

        // Gửi thông báo hoàn thành đơn hàng (luôn gửi, kể cả khi không có điểm)
        NotificationRequest notification = new NotificationRequest();
        notification.setTitle("Order Completed");
        StringBuilder content = new StringBuilder("Your order with order code: " + order.getOrderCode() + " has been completed.");

        if (pointsEarned <= 0) {
            log.debug("Order ID: {} total price {} is too low to earn points.", order.getId(), totalPrice);
            notificationService.sendAndSaveNotification(notification, user.getEmail());
            log.debug("Sent notification for order completion without loyalty points for Order ID: {}", order.getId());
            return;
        }

        // Kiểm tra xem đã tích điểm cho đơn hàng này chưa
        boolean alreadyEarned = loyaltyPointTransactionRepository.existsByOrder_Id(order.getId());
        if (alreadyEarned) {
            log.warn("Loyalty points already earned for Order ID: {}. Skipping.", order.getId());
            notificationService.sendAndSaveNotification(notification, user.getEmail());
            log.debug("Sent notification for order completion without new loyalty points for Order ID: {}", order.getId());
            return;
        }

        // Tạo giao dịch điểm thưởng
        LoyaltyPointTransaction transaction = LoyaltyPointTransaction.builder()
                .user(user)
                .order(order)
                .pointsChange(pointsEarned)
                .transactionType(ELoyaltyTransactionType.EARNED)
                .description("Earned " + pointsEarned + " points from order #" + order.getOrderCode())
                .build();

        loyaltyPointTransactionRepository.save(transaction);
        log.debug("Saved loyalty point transaction for Order ID: {}. Points: {}", order.getId(), pointsEarned);

        // Cập nhật số dư điểm thưởng của người dùng
        Integer currentBalance = user.getLoyaltyPointsBalance();
        int newBalance = (currentBalance != null ? currentBalance : 0) + pointsEarned;
        user.setLoyaltyPointsBalance(newBalance);
        userRepository.save(user);
        log.debug("Updated loyalty points balance for User ID: {}. New balance: {}", user.getId(), newBalance);

        // Thêm thông tin điểm thưởng vào nội dung thông báo
        content.append(" You have earned ").append(pointsEarned).append(" loyalty points.");
        notification.setContent(content.toString());
        notificationService.sendAndSaveNotification(notification, user.getEmail());
        log.debug("Sent notification for order completion with loyalty points for Order ID: {}", order.getId());
    }

    private void clearCartItemsAfterOrder(Cart cart, Long userId, Long orderId) {
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return;
        }
        try {
            log.debug("Attempting to clear cart for user ID: {} after creating order ID: {}", userId, orderId);
            List<Long> cartItemOptionIdsToDelete = new ArrayList<>();
            List<Long> cartItemIdsToDelete = new ArrayList<>();

            for (CartItem cartItem : cart.getCartItems()) {
                cartItemIdsToDelete.add(cartItem.getId());
                if (cartItem.getCartItemOptions() != null) {
                    cartItem.getCartItemOptions().forEach(cio -> cartItemOptionIdsToDelete.add(cio.getId()));
                }
            }

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
            log.error("Error clearing cart for user ID: {} after creating order ID: {}. Error: {}", userId, orderId, e.getMessage(), e);
        }
    }

    private String generateOrderCode() {
        long timestamp = Instant.now().toEpochMilli();
        int randomNum = (int) (Math.random() * 10000);
        String code = timestamp + String.format("%04d", randomNum);
        String numericPart = code.substring(Math.max(0, code.length() - 10));
        return "ORDER-" + numericPart;
    }
}