package com.foodApp.entity;

import com.foodApp.util.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "delivery_name")
    private String deliveryName;
    @Column(name = "delivery_phone")
    private String deliveryPhone;
    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_at")
    private LocalDateTime orderAt;

    @Column(name = "total_amount")
    private Double totalAmount;


    @Column(name = "loyalty_points_earned")
    private int loyaltyPointsEarned;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
