package com.foodApp.service;

import com.foodApp.dto.response.OrderResponse;

public interface PaymentService {
    OrderResponse processPayment(Long orderId);
    OrderResponse refundPayment(Long orderId);
}
