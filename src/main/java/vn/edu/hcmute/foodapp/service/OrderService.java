package vn.edu.hcmute.foodapp.service;

import vn.edu.hcmute.foodapp.dto.request.CreateOrderRequest;
import vn.edu.hcmute.foodapp.dto.response.OrderInfoResponse;

public interface OrderService {
    OrderInfoResponse createOrder(Long userId, CreateOrderRequest request);
}
