package vn.edu.hcmute.foodapp.service;

import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.PaymentRequest;
import vn.edu.hcmute.foodapp.dto.response.PaymentResponse;

public interface PaymentService {
    @Transactional(rollbackFor = Exception.class)
    PaymentResponse createPayment(PaymentRequest request);
}
