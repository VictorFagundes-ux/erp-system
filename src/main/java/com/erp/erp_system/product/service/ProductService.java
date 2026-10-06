package com.erp.erp_system.product.service;


import com.erp.erp_system.inventory.model.Inventory;
import com.erp.erp_system.inventory.repositoty.InventoryRepository;
import com.erp.erp_system.product.model.Product;
import com.erp.erp_system.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    // Injeção de dependência via construtor (Boa prática recomendada)
    public ProductService(ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public Product createProduct(Product product) {
        if (productRepository.existsBySku(product.getSku())) {
            throw new IllegalArgumentException("Product with SKU " + product.getSku() + " already exists.");
        }
        Product savedProduct = productRepository.save(product);

        // Cria automaticamente o registro de estoque zerado para o novo produto
        Inventory inventory = new Inventory();
        inventory.setProduct(savedProduct);
        inventory.setQuantity(0);
        inventory.setMinQuantity(5);
        inventoryRepository.save(inventory);

        return savedProduct;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        // Nota: O SKU geralmente não é alterado livremente, mas podemos atualizar se necessário.

        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}