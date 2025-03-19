package com.foodApp.service.impl;

import com.foodApp.dto.request.OrderItemRequest;
import com.foodApp.dto.request.OrderRequest;
import com.foodApp.dto.response.OrderResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.entity.*;
import com.foodApp.mapper.OrderItemMapper;
import com.foodApp.mapper.OrderMapper;
import com.foodApp.repository.OptionValueRepository;
import com.foodApp.repository.OrderRepository;
import com.foodApp.service.*;
import com.foodApp.util.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final BranchService branchService;
    private final MenuItemService menuItemService;
    private final OptionValueRepository optionValueRepository;
    private final OrderItemMapper orderItemMapper;
    private final CartService cartService;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        User user = userService.findByEmail(getUsername());
        Branch branch = branchService.findById(request.getBranchId());

        Order order = orderMapper.toEntity(request);
        order.setUser(user);
        order.setBranch(branch);
        order.setOrderStatus(OrderStatus.PENDING);

        // Tạo Payment với trạng thái PENDING từ enum
        Payment payment = Payment.builder()
                .amount(0.0) // Sẽ cập nhật sau khi tính totalAmount
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase()))
                .paymentStatus(PaymentStatus.PENDING) // Sử dụng enum
                .order(order)
                .build();
        order.setPayment(payment);

        Set<OrderItem> orderItems = new HashSet<>();
        if (request.getItem() != null) {
            orderItems.add(createOrderItemFromRequest(request.getItem()));
        } else {
            Cart cart = cartService.findByUsername(getUsername());
            if (cart == null || cart.getCartItems().isEmpty()) {
                throw new IllegalStateException("Cart is empty or not found");
            }
            orderItems = orderItemMapper.cartItemsToOrderItems(cart.getCartItems());
            cartService.clearCart();
        }

        order.setOrderItems(orderItems);
        updateOrderTotals(order);
        payment.setAmount(order.getTotalAmount()); // Cập nhật amount cho Payment

        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Transactional
    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getOrderStatus() == OrderStatus.CANCELED || order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot update status of CANCELED or DELIVERED orders");
        }

        // Kiểm tra thanh toán trước khi chuyển sang DELIVERED
        if (newStatus == OrderStatus.DELIVERED && order.getPayment() != null) {
            PaymentStatus currentPaymentStatus = order.getPayment().getPaymentStatus();
            PaymentMethod paymentMethod = order.getPayment().getPaymentMethod();

            if (paymentMethod != PaymentMethod.COD && currentPaymentStatus != PaymentStatus.PAID) {
                throw new IllegalStateException("Order must be PAID before marking as DELIVERED (non-COD payment)");
            }
        }

        // Xử lý khi hủy đơn hàng
        if (newStatus == OrderStatus.CANCELED && order.getPayment() != null) {
            if (order.getPayment().getPaymentStatus() == PaymentStatus.PAID) {
                order.getPayment().setPaymentStatus(PaymentStatus.REFUNDED); // Chuyển sang REFUNDED nếu đã thanh toán
            } else if (order.getPayment().getPaymentStatus() == PaymentStatus.PENDING) {
                order.getPayment().setPaymentStatus(PaymentStatus.CANCELLED); // Chuyển sang CANCELLED nếu chưa thanh toán
            }
        }

        // Xử lý khi giao hàng với COD
        if (newStatus == OrderStatus.DELIVERED && order.getPayment() != null
                && order.getPayment().getPaymentMethod() == PaymentMethod.COD
                && order.getPayment().getPaymentStatus() == PaymentStatus.PENDING) {
            order.getPayment().setPaymentStatus(PaymentStatus.PAID); // COD chuyển sang PAID khi giao hàng
        }

        order.setOrderStatus(newStatus);
        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Transactional
    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest request) {
        Order order = orderRepository.findByIdAndUser_Email(orderId, getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Order not found or not authorized"));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only PENDING orders can be updated");
        }

        if (request.getBranchId() != null) {
            Branch newBranch = branchService.findById(request.getBranchId());
            order.setBranch(newBranch);
        }
        if (request.getDeliveryName() != null) {
            order.setDeliveryName(request.getDeliveryName());
        }
        if (request.getDeliveryPhone() != null) {
            order.setDeliveryPhone(request.getDeliveryPhone());
        }
        if (request.getDeliveryAddress() != null) {
            order.setDeliveryAddress(request.getDeliveryAddress());
        }
        if (request.getOrderType() != null) {
            order.setOrderType(OrderType.valueOf(request.getOrderType().toUpperCase()));
        }
        if (request.getPaymentMethod() != null) {
            order.getPayment().setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase()));
        }
        if (request.getItem() != null) {
            Set<OrderItem> updatedOrderItems = new HashSet<>();
            updatedOrderItems.add(createOrderItemFromRequest(request.getItem()));
            order.getOrderItems().clear();
            order.getOrderItems().addAll(updatedOrderItems);
        }

        updateOrderTotals(order);
        order.getPayment().setAmount(order.getTotalAmount());

        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findByIdAndUser_Email(orderId, getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Order not found or not authorized"));
        return orderMapper.toResponse(order);
    }

    @Override
    public PageResponse<OrderResponse> getAllOrdersByUser(int page, int size, String sort, String direction) {
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<Order> orderPage = orderRepository.findByUser_Email(getUsername(), pageable);

        return PageResponse.<OrderResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(orderPage.getTotalPages())
                .totalElements(orderPage.getTotalElements())
                .content(orderPage.getContent().stream().map(orderMapper::toResponse).toList())
                .build();
    }

    @Override
    public PageResponse<OrderResponse> getAllOrders(int page, int size, String sort, String direction) {
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<Order> orderPage = orderRepository.findAll(pageable);

        return PageResponse.<OrderResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(orderPage.getTotalPages())
                .totalElements(orderPage.getTotalElements())
                .content(orderPage.getContent().stream().map(orderMapper::toResponse).toList())
                .build();
    }

    @Transactional
    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findByIdAndUser_Email(orderId, getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Order not found or not authorized"));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only PENDING orders can be canceled");
        }

        order.setOrderStatus(OrderStatus.CANCELED);
        if (order.getPayment() != null) {
            //Xử lý hoàn tiền khi đã thanh toán và chưa thanh toán
            if (order.getPayment().getPaymentStatus() == PaymentStatus.PAID) {
                order.getPayment().setPaymentStatus(PaymentStatus.REFUNDED);
            } else if (order.getPayment().getPaymentStatus() == PaymentStatus.PENDING) {
                order.getPayment().setPaymentStatus(PaymentStatus.CANCELLED);
            }
        }

        orderRepository.save(order);
    }

    private OrderItem createOrderItemFromRequest(OrderItemRequest itemRequest) {
        MenuItem menuItem = menuItemService.findById(itemRequest.getMenuItemId());
        Set<OptionValue> selectedOptions = new HashSet<>(optionValueRepository.findAllById(itemRequest.getSelectedOptionIds()));
        if (selectedOptions.size() != itemRequest.getSelectedOptionIds().size()) {
            throw new IllegalArgumentException("One or more option values not found");
        }
        OrderItem orderItem = orderItemMapper.toEntity(itemRequest);
        orderItem.setMenuItem(menuItem);
        orderItem.setSelectedOptions(selectedOptions);
        orderItem.setPriceAtOrder(menuItem.getBasePrice() + selectedOptions.stream().mapToDouble(OptionValue::getExtraCost).sum());
        return orderItem;
    }

    private void updateOrderTotals(Order order) {
        double totalAmount = order.getOrderItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPriceAtOrder())
                .sum();
        order.setTotalAmount(totalAmount);
        order.setLoyaltyPointsEarned(calculateLoyaltyPoints(totalAmount));
    }

    private int calculateLoyaltyPoints(double totalAmount) {
        return (int) Math.floor(totalAmount / 10);
    }

    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() instanceof String) {
            throw new SecurityException("No authenticated user found");
        }
        return ((UserDetails) auth.getPrincipal()).getUsername();
    }
}