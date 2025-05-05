package vn.edu.hcmute.foodapp.service;

import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.response.ShipmentDetailResponse;

public interface ShipmentService {
    @Transactional(readOnly = true)
    ShipmentDetailResponse getShipmentById(Long id);
}
