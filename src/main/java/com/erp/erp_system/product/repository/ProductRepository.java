package com.erp.erp_system.product.repository;



import com.erp.erp_system.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Metodo customizado para buscar produto pelo SKU
    Optional<Product> findBySku(String sku);

    // Valida se já existe um produto cadastrado com esse SKU
    boolean existsBySku(String sku);
}