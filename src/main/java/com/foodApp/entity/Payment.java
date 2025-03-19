package com.foodApp.entity;

import com.foodApp.util.PaymentMethod;
import com.foodApp.util.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_payment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends AbstractEntity<Long> {
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // "CREDIT_CARD", "PAYPAL", "CASH_ON_DELIVERY"

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // "PENDING", "PAID", "FAILED", "REFUNDED"

    @Column
    private String transactionId; // Mã giao dịch từ cổng thanh toán

    @Column
    private String errorMessage; // Lưu thông báo lỗi nếu thanh toán thất bại
}
