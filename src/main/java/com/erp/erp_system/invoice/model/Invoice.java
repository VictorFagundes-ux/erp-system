package com.erp.erp_system.invoice.model;

import com.erp.erp_system.order.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(name = "invoice_number", nullable = false, length = 20)
    private String invoiceNumber;

    @Column(nullable = false, length = 10)
    private String series = "1";

    @Column(name = "access_key", unique = true, length = 44)
    private String accessKey; // Ex: 412610... (44 caracteres únicos)

    @Column(nullable = false)
    private String status = "AUTHORIZED"; // AUTHORIZED, CANCELLED

    @Column(name = "xml_content", columnDefinition = "TEXT")
    private String xmlContent; // O XML fiscal simulado

    @CreationTimestamp
    @Column(name = "issued_at", updatable = false)
    private LocalDateTime issuedAt;
}