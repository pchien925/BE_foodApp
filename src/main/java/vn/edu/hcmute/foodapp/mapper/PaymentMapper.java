package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.request.PaymentRequest;
import vn.edu.hcmute.foodapp.dto.response.PaymentInfoResponse;
import vn.edu.hcmute.foodapp.dto.response.PaymentResponse;
import vn.edu.hcmute.foodapp.entity.Payment;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {OrderMapper.class})
public interface PaymentMapper {
    PaymentMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(PaymentMapper.class);

    Payment toEntity(PaymentRequest request);

    PaymentInfoResponse toInfoResponse(Payment payment);

    PaymentResponse toResponse(Payment payment);
}
