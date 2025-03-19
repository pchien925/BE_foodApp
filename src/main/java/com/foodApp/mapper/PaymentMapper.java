package com.foodApp.mapper;

import com.foodApp.dto.response.PaymentResponse;
import com.foodApp.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponse toResponse(Payment entity);
}
