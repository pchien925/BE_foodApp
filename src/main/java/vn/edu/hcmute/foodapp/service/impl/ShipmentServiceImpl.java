package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.response.ShipmentDetailResponse;
import vn.edu.hcmute.foodapp.entity.Shipment;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.ShipmentMapper;
import vn.edu.hcmute.foodapp.repository.ShipmentRepository;
import vn.edu.hcmute.foodapp.service.ShipmentService;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;

    @Transactional(readOnly = true)
    @Override
    public ShipmentDetailResponse getShipmentById(Long id) {
        log.info("Fetching shipment detail for shipment ID: {}", id);
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with ID: " + id));
        return ShipmentMapper.INSTANCE.toDetailResponse(shipment);
    }
}
