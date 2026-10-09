package com.erp.erp_system.invoice.controller;

import com.erp.erp_system.invoice.model.Invoice;
import com.erp.erp_system.invoice.service.InvoiceService;
import com.erp.erp_system.order.model.Order;
import com.erp.erp_system.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final OrderService orderService;

    public InvoiceController(InvoiceService invoiceService, OrderService orderService) {
        this.invoiceService = invoiceService;
        this.orderService = orderService;
    }

    // Endpoint para emitir/gerar a Nota Fiscal de um Pedido específico
    @PostMapping("/order/{orderId}")
    public ResponseEntity<Invoice> generateInvoiceForOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        Invoice invoice = invoiceService.generateInvoiceForOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
    }

    // Endpoint para buscar a Nota Fiscal pelo ID da Nota
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        // Se precisar de um método getInvoiceById no service, basta adicioná-lo no InvoiceService
        Invoice invoice = invoiceService.getInvoiceByOrderId(id); // ou buscar direto pelo repositório se preferir
        return ResponseEntity.ok(invoice);
    }

    // Endpoint para buscar a Nota Fiscal através do ID do Pedido
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Invoice> getInvoiceByOrderId(@PathVariable Long orderId) {
        Invoice invoice = invoiceService.getInvoiceByOrderId(orderId);
        return ResponseEntity.ok(invoice);
    }
}