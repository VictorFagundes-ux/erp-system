package com.erp.erp_system.inventory.controller;

import com.erp.erp_system.inventory.model.Inventory;
import com.erp.erp_system.inventory.service.InventoryService;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Consulta o estoque pelo ID do produto
    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        Inventory inventory = inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.ok(inventory);
    }

    // Adiciona quantidade ao estoque (Entrada)
    @PatchMapping("/product/{productId}/add")
    public ResponseEntity<Inventory> addStock(
            @PathVariable Long productId,
            @RequestParam @Min(value = 1, message = "Amount must be at least 1") int amount) {
        Inventory updatedInventory = inventoryService.addStock(productId, amount);
        return ResponseEntity.ok(updatedInventory);
    }

    // Remove quantidade do estoque (Saída)
    @PatchMapping("/product/{productId}/remove")
    public ResponseEntity<Inventory> removeStock(
            @PathVariable Long productId,
            @RequestParam @Min(value = 1, message = "Amount must be at least 1") int amount) {
        Inventory updatedInventory = inventoryService.removeStock(productId, amount);
        return ResponseEntity.ok(updatedInventory);
    }
}