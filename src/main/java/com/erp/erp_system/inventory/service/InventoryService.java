package com.erp.erp_system.inventory.service;



import com.erp.erp_system.inventory.model.Inventory;
import com.erp.erp_system.inventory.repositoty.InventoryRepository;

import com.erp.erp_system.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductService productService;

    public InventoryService(InventoryRepository inventoryRepository, ProductService productService) {
        this.inventoryRepository = inventoryRepository;
        this.productService = productService;
    }

    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product id: " + productId));
    }

    @Transactional
    public Inventory addStock(Long productId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to add must be greater than zero.");
        }
        Inventory inventory = getInventoryByProductId(productId);
        inventory.setQuantity(inventory.getQuantity() + amount);
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public Inventory removeStock(Long productId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to remove must be greater than zero.");
        }
        Inventory inventory = getInventoryByProductId(productId);

        if (inventory.getQuantity() < amount) {
            throw new IllegalArgumentException("Insufficient stock. Current quantity: " + inventory.getQuantity());
        }

        inventory.setQuantity(inventory.getQuantity() - amount);
        return inventoryRepository.save(inventory);
    }
}