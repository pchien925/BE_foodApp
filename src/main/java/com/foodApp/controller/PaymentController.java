package com.foodApp.controller;

import com.foodApp.dto.response.OrderResponse;
import com.foodApp.dto.response.ResponseData;
import com.foodApp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{orderId}")
    public ResponseData<OrderResponse> processPayment(@PathVariable Long orderId) {
        return ResponseData.<OrderResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Payment processed successfully")
                .data(paymentService.processPayment(orderId))
                .build();
    }

    @PostMapping("/{orderId}/refund")
    public ResponseData<OrderResponse> refundPayment(@PathVariable Long orderId) {
        return ResponseData.<OrderResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Payment refunded successfully")
                .data(paymentService.refundPayment(orderId))
                .build();
    }
}