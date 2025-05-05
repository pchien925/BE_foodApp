package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.PaymentRequest;
import vn.edu.hcmute.foodapp.dto.response.PaymentResponse;
import vn.edu.hcmute.foodapp.entity.Payment;
import vn.edu.hcmute.foodapp.mapper.PaymentMapper;
import vn.edu.hcmute.foodapp.repository.PaymentRepository;
import vn.edu.hcmute.foodapp.service.PaymentService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        log.info("Creating new Payment with request: {}", request);
        Payment payment = PaymentMapper.INSTANCE.toEntity(request);

        return PaymentMapper.INSTANCE.toResponse(paymentRepository.save(payment));
    }
}
