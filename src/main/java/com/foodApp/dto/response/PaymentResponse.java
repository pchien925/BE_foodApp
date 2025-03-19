package com.foodApp.dto.response;

import com.foodApp.util.PaymentMethod;
import com.foodApp.util.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentResponse {
    private Long id;
    private Double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
