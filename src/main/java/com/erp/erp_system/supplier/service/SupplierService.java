package com.erp.erp_system.supplier.service;

import com.erp.erp_system.supplier.model.Supplier;
import com.erp.erp_system.supplier.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
    }

    public Supplier saveSupplier(Supplier supplier) {
        supplierRepository.findByDocument(supplier.getDocument()).ifPresent(existing -> {
            if (supplier.getId() == null || !existing.getId().equals(supplier.getId())) {
                throw new RuntimeException("Supplier with document " + supplier.getDocument() + " already exists.");
            }
        });
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = getSupplierById(id);
        supplierRepository.delete(supplier);
    }
}
