package com.foodApp.service;

import com.foodApp.dto.request.OrderRequest;
import com.foodApp.dto.response.OrderResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.util.OrderStatus;
import jakarta.transaction.Transactional;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    @Transactional
    OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus);

    @Transactional
    OrderResponse updateOrder(Long orderId, OrderRequest request);

    OrderResponse getOrderById(Long orderId);

    PageResponse<OrderResponse> getAllOrdersByUser(int page, int size, String sort, String direction);

    PageResponse<OrderResponse> getAllOrders(int page, int size, String sort, String direction);

    @Transactional
    void cancelOrder(Long orderId);
}
