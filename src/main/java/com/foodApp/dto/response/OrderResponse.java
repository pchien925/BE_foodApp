package com.foodApp.dto.response;

import com.foodApp.util.OrderStatus;
import com.foodApp.util.OrderType;
import com.foodApp.util.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
public class OrderResponse {
    private Long id;
    private String deliveryName;
    private String deliveryPhone;
    private String deliveryAddress;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private Double totalAmount;
    private int loyaltyPointsEarned;
    private Set<OrderItemResponse> orderItems;
    private Long userId;
    private Long branchId;
    private PaymentResponse payment;
}
