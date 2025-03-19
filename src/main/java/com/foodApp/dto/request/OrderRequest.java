package com.foodApp.dto.request;

import com.foodApp.util.EnumValue;
import com.foodApp.util.OrderType;
import com.foodApp.util.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequest {
    @NotNull(message = "Branch ID cannot be null")
    @Min(value = 1, message = "Branch ID must be greater than 0")
    private Long branchId;

    private String deliveryName;
    private String deliveryPhone;
    private String deliveryAddress;

    @EnumValue(name = "paymentMethod", enumClass = PaymentMethod.class)
    private String paymentMethod;

    @EnumValue(name = "orderType", enumClass = OrderType.class)
    private String orderType;

    private OrderItemRequest item;
}
