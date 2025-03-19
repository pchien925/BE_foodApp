package com.foodApp.entity;

import com.foodApp.util.OrderStatus;
import com.foodApp.util.OrderType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_order")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractEntity<Long> {
    @Column(name = "delivery_name")
    private String deliveryName;

    @Column(name = "delivery_phone")
    private String deliveryPhone;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "loyalty_points_earned")
    private int loyaltyPointsEarned;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payment payment;
}