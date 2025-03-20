package com.foodApp.service.impl;

import com.foodApp.dto.response.OrderResponse;
import com.foodApp.entity.Order;
import com.foodApp.entity.Payment;
import com.foodApp.exception.PaymentException;
import com.foodApp.mapper.OrderMapper;
import com.foodApp.repository.OrderRepository;
import com.foodApp.repository.PaymentRepository;
import com.foodApp.service.PaymentService;
import com.foodApp.util.OrderStatus;
import com.foodApp.util.PaymentMethod;
import com.foodApp.util.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderResponse processPayment(Long orderId) {
        Order order = orderRepository.findByIdAndUser_Email(orderId, getUsername())
                .orElseThrow(() -> new PaymentException("Order not found or not authorized"));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new PaymentException("Only PENDING orders can be processed for payment");
        }

        Payment payment = order.getPayment();
        if (payment == null) {
            throw new PaymentException("No payment associated with this order");
        }
        if (payment.getPaymentStatus() == PaymentStatus.PAID) {
            throw new PaymentException("Order has already been paid");
        }

        if (payment.getPaymentMethod() == PaymentMethod.CARD || payment.getPaymentMethod() == PaymentMethod.MOBILE) {
            try {
                String transactionId = processOnlinePayment(payment.getAmount());
                payment.setTransactionId(transactionId);
                payment.setPaymentStatus(PaymentStatus.PAID);
                payment.setErrorMessage(null);
                order.setOrderStatus(OrderStatus.CONFIRMED);
            } catch (Exception e) {
                payment.setPaymentStatus(PaymentStatus.FAILED);
                payment.setErrorMessage("Payment failed: " + e.getMessage());
                throw new PaymentException("Payment processing failed", e);
            }
        } else if (payment.getPaymentMethod() == PaymentMethod.COD) {
            payment.setPaymentStatus(PaymentStatus.PENDING);
            order.setOrderStatus(OrderStatus.CONFIRMED);
        } else {
            throw new PaymentException("Unsupported payment method: " + payment.getPaymentMethod());
        }

        paymentRepository.save(payment);
        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Transactional
    @Override
    public OrderResponse refundPayment(Long orderId) {
        Order order = orderRepository.findByIdAndUser_Email(orderId, getUsername())
                .orElseThrow(() -> new PaymentException("Order not found or not authorized"));

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new PaymentException("Cannot refund a DELIVERED order");
        }

        Payment payment = order.getPayment();
        if (payment == null) {
            throw new PaymentException("No payment associated with this order");
        }
        if (payment.getPaymentStatus() != PaymentStatus.PAID) {
            throw new PaymentException("Order has not been paid or already refunded");
        }

        if (payment.getPaymentMethod() == PaymentMethod.CARD || payment.getPaymentMethod() == PaymentMethod.MOBILE) {
            try {
                processRefund(payment.getTransactionId(), payment.getAmount());
                payment.setPaymentStatus(PaymentStatus.REFUNDED);
                order.setOrderStatus(OrderStatus.CANCELED);
            } catch (Exception e) {
                payment.setErrorMessage("Refund failed: " + e.getMessage());
                throw new PaymentException("Refund processing failed", e);
            }
        } else if (payment.getPaymentMethod() == PaymentMethod.COD) {
            throw new PaymentException("Cannot refund CASH_ON_DELIVERY payment before delivery");
        }

        paymentRepository.save(payment);
        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    private String processOnlinePayment(Double amount) {
        // Giả lập gọi API cổng thanh toán (Stripe, Paypal, etc.)
        return "TXN_" + System.currentTimeMillis();
    }

    private void processRefund(String transactionId, Double amount) {
        // Giả lập gọi API hoàn tiền
        // Ở đây bạn có thể tích hợp với cổng thanh toán thực tế
        if (transactionId == null) {
            throw new IllegalArgumentException("No transaction ID available for refund");
        }
    }

    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() instanceof String) {
            throw new SecurityException("No authenticated user found");
        }
        return ((UserDetails) auth.getPrincipal()).getUsername();
    }
}