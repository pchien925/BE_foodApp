package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.response.PaymentInfoResponse;
import vn.edu.hcmute.foodapp.entity.Payment;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
    PaymentMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(PaymentMapper.class);

    PaymentInfoResponse toInfoResponse(Payment payment);
}
