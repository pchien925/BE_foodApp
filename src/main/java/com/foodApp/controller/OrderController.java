package com.foodApp.controller;

import com.foodApp.dto.request.OrderRequest;
import com.foodApp.dto.response.OrderResponse;
import com.foodApp.dto.response.PageResponse;
import com.foodApp.dto.response.ResponseData;
import com.foodApp.service.OrderService;
import com.foodApp.util.OrderStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseData<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
        return ResponseData.<OrderResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Order created successfully")
                .data(orderService.createOrder(request))
                .build();
    }

    @GetMapping("/{orderId}")
    public ResponseData<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseData.<OrderResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Order retrieved successfully")
                .data(orderService.getOrderById(orderId))
                .build();
    }

    @GetMapping("/me")
    public ResponseData<PageResponse<OrderResponse>> getCurrentUserOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseData.<PageResponse<OrderResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Current user orders retrieved successfully")
                .data(orderService.getAllOrdersByUser(page, size, sort, direction))
                .build();
    }

    @GetMapping
    public ResponseData<PageResponse<OrderResponse>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseData.<PageResponse<OrderResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("All orders retrieved successfully")
                .data(orderService.getAllOrders(page, size, sort, direction))
                .build();
    }

    @PutMapping("/{orderId}")
    public ResponseData<OrderResponse> updateOrder(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderRequest request) {
        return ResponseData.<OrderResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Order updated successfully")
                .data(orderService.updateOrder(orderId, request))
                .build();
    }

    @PutMapping("/{orderId}/status")
    public ResponseData<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam("status") OrderStatus status) {
        return ResponseData.<OrderResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Order status updated successfully")
                .data(orderService.updateOrderStatus(orderId, status))
                .build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseData<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Order canceled successfully")
                .build();
    }
}