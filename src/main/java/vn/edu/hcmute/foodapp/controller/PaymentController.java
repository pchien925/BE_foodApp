package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmute.foodapp.dto.request.PaymentRequest;
import vn.edu.hcmute.foodapp.dto.response.PaymentResponse;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j(topic = "PAYMENT_CONTROLLER")
@Tag(name = "Payment", description = "Payment API for authenticated users")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseData<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest request) {
        log.info("Creating payment with request: {}", request);
        PaymentResponse paymentResponse = paymentService.createPayment(request);
        return ResponseData.<PaymentResponse>builder()
                .data(paymentResponse)
                .message("Payment created successfully")
                .build();
    }
}
