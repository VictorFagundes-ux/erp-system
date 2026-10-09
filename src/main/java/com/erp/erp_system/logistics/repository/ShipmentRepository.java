package com.erp.erp_system.logistics.repository;

import com.erp.erp_system.logistics.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    // Busca a remessa vinculada ao ID de um pedido específico
    Optional<Shipment> findByOrderId(Long orderId);

    // Busca a remessa através do código de rastreio
    Optional<Shipment> findByTrackingCode(String trackingCode);
}