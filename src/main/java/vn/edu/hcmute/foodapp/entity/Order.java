package vn.edu.hcmute.foodapp.entity;

import vn.edu.hcmute.foodapp.util.enumeration.EOrderStatus;
import vn.edu.hcmute.foodapp.util.enumeration.EPaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_order")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractEntity<Long>{
    @Column(name = "order_code", unique = true)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", nullable = false)
    private Branch branch;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "note")
    private String note;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private EPaymentMethod paymentMethod;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private EOrderStatus orderStatus;

    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<OrderItem> orderItems = new HashSet<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Shipment shipment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Payment> payments = new HashSet<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private LoyaltyPointTransaction loyaltyTransaction;
}
