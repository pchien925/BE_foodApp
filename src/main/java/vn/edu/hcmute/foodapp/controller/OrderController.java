package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.CreateOrderRequest;
import vn.edu.hcmute.foodapp.dto.response.OrderInfoResponse;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API for managing orders")
@Slf4j(topic = "ORDER_CONTROLLER")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseData<OrderInfoResponse> createOrder(@RequestParam Long userId, @RequestBody CreateOrderRequest request) {
        log.info("Create order for user with id: {}", userId);
        OrderInfoResponse orderInfoResponse = orderService.createOrder(userId, request);
        return ResponseData.<OrderInfoResponse>builder()
                .data(orderInfoResponse)
                .message("Order created successfully")
                .build();
    }
}
