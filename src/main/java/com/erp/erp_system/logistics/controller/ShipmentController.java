package com.erp.erp_system.logistics.controller;

import com.erp.erp_system.logistics.model.Shipment;
import com.erp.erp_system.logistics.service.ShipmentService;
import com.erp.erp_system.order.model.Order;
import com.erp.erp_system.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final OrderService orderService;

    public ShipmentController(ShipmentService shipmentService, OrderService orderService) {
        this.shipmentService = shipmentService;
        this.orderService = orderService;
    }

    // Cria/Inicia o processo de expedição e transporte para um pedido aprovado
    @PostMapping("/order/{orderId}")
    public ResponseEntity<Shipment> createShipment(
            @PathVariable Long orderId,
            @RequestParam(required = false) String carrierName) {

        Order order = orderService.getOrderById(orderId);
        Shipment shipment = shipmentService.createShipmentForOrder(order, carrierName);
        return ResponseEntity.status(HttpStatus.CREATED).body(shipment);
    }

    // Consulta os detalhes da remessa pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id) {
        Shipment shipment = shipmentService.getShipmentById(id);
        return ResponseEntity.ok(shipment);
    }

    // Atualiza o status logístico (Separação -> Expedição -> Em Rota/Dispatched -> Entregue/Delivered)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Shipment> updateShipmentStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Shipment updatedShipment = shipmentService.updateShipmentStatus(id, status);
        return ResponseEntity.ok(updatedShipment);
    }
}