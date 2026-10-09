package com.erp.erp_system.logistics.service;

import com.erp.erp_system.logistics.model.Shipment;
import com.erp.erp_system.logistics.repository.ShipmentRepository;
import com.erp.erp_system.order.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional
    public Shipment createShipmentForOrder(Order order, String carrierName) {
        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        shipment.setCarrierName(carrierName != null ? carrierName : "Internal Fleet");
        shipment.setTrackingCode("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        shipment.setStatus("PENDING_SEPARATION");

        return shipmentRepository.save(shipment);
    }

    @Transactional
    public Shipment updateShipmentStatus(Long shipmentId, String newStatus) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + shipmentId));

        shipment.setStatus(newStatus.toUpperCase());

        if ("DISPATCHED".equals(shipment.getStatus())) {
            shipment.setDispatchedAt(LocalDateTime.now());
        } else if ("DELIVERED".equals(shipment.getStatus())) {
            shipment.setDeliveredAt(LocalDateTime.now());
        }

        return shipmentRepository.save(shipment);
    }

    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
    }
}
