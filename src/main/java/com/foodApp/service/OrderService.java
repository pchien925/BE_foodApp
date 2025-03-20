package com.foodApp.service;

import com.foodApp.dto.request.OrderRequest;
import com.foodApp.dto.response.OrderResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.util.OrderStatus;
import com.foodApp.util.OrderType;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    @Transactional
    OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus);

    @Transactional
    OrderResponse updateOrder(Long orderId, OrderRequest request);

    OrderResponse getOrderById(Long orderId);

    PageResponse<OrderResponse> getAllOrdersByUser(int page, int size, String sort, String direction);

    PageResponse<OrderResponse> getAllOrders(int page, int size, String sort, String direction);

    PageResponse<OrderResponse> searchOrders(String query, OrderStatus status, OrderType type, Double minAmount, Double maxAmount, Long userId, Long branchId, int page, int size, String sort, String direction);

    @Transactional
    void cancelOrder(Long orderId);
}
