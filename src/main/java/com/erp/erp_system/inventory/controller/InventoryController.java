package com.erp.erp_system.inventory.controller;

import com.erp.erp_system.inventory.model.Inventory;
import com.erp.erp_system.inventory.service.InventoryService;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/{productId}/add")
    public ResponseEntity<Inventory> addStock(
            @PathVariable Long productId,
            @RequestParam @Min(1) Integer quantity) {
        Inventory updatedInventory = inventoryService.addStock(productId, quantity);
        return ResponseEntity.ok(updatedInventory);
    }

    @PostMapping("/{productId}/remove")
    public ResponseEntity<Inventory> removeStock(
            @PathVariable Long productId,
            @RequestParam @Min(1) Integer quantity) {
        Inventory updatedInventory = inventoryService.removeStock(productId, quantity);
        return ResponseEntity.ok(updatedInventory);
    }
}