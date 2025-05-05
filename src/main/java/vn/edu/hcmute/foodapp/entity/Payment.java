package vn.edu.hcmute.foodapp.entity;

import vn.edu.hcmute.foodapp.util.enumeration.EPaymentMethod;
import vn.edu.hcmute.foodapp.util.enumeration.EPaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_payment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends AbstractEntity<Long> {
    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private EPaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EPaymentStatus status;

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "gateway_response")
    private String gatewayResponse;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
