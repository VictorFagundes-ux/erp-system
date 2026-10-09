package com.erp.erp_system.invoice.service;

import com.erp.erp_system.invoice.model.Invoice;
import com.erp.erp_system.invoice.repository.InvoiceRepository;
import com.erp.erp_system.order.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional
    public Invoice generateInvoiceForOrder(Order order) {
        // Verifica se já existe nota para este pedido
        if (invoiceRepository.findByOrderId(order.getId()).isPresent()) {
            throw new IllegalArgumentException("Invoice already emitted for order id: " + order.getId());
        }

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setInvoiceNumber(generateSequentialInvoiceNumber());
        invoice.setAccessKey(generateMockAccessKey());
        invoice.setStatus("AUTHORIZED");
        invoice.setXmlContent(generateMockXml(order));

        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Invoice not found for order id: " + orderId));
    }

    // Métodos auxiliares de simulação (Em produção, aqui entraria a integração com a SEFAZ)
    private String generateSequentialInvoiceNumber() {
        return String.valueOf((int) (Math.random() * 900000 + 100000)); // Número aleatório de 6 dígitos
    }

    private String generateMockAccessKey() {
        // Uma chave de acesso real tem 44 dígitos numéricos
        return UUID.randomUUID().toString().replace("-", "").substring(0, 44);
    }

    private String generateMockXml(Order order) {
        return "<?xmlversion=\"1.0\" encoding=\"UTF-8\"?>" +
                "<nfeProc xmlns=\"http://www.portalfiscal.inf.br/nfe\">" +
                "<NFe><infNFe Id=\"NFe" + generateMockAccessKey() + "\">" +
                "<total><ICMSTot><vNF>" + order.getTotalAmount() + "</vNF></ICMSTot></total>" +
                "</infNFe></NFe></nfeProc>";
    }
}

