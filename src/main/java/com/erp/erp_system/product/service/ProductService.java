package com.erp.erp_system.product.service;


import com.erp.erp_system.product.model.Product;
import com.erp.erp_system.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Injeção de dependência via construtor (Boa prática recomendada)
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(Product product) {
        if (productRepository.existsBySku(product.getSku())) {
            throw new IllegalArgumentException("Product with SKU " + product.getSku() + " already exists.");
        }
        return productRepository.save(product);
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