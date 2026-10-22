package com.erp.erp_system.service;

import com.erp.erp_system.inventory.repository.InventoryRepository;
import com.erp.erp_system.product.model.Product;
import com.erp.erp_system.product.repository.ProductRepository;
import com.erp.erp_system.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should create product successfully when SKU is unique")
    void createProduct_Success() {
        // Arrange (Preparação)
        Product product = new Product();
        product.setName("Coca-Cola 2L");
        product.setSku("COCA-2L-001");
        product.setPrice(BigDecimal.valueOf(10.00));

        when(productRepository.existsBySku("COCA-2L-001")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act (Ação)
        Product savedProduct = productService.createProduct(product);

        // Assert (Verificação)
        assertNotNull(savedProduct);
        assertEquals("Coca-Cola 2L", savedProduct.getName());
        verify(productRepository, times(1)).existsBySku("COCA-2L-001");
        verify(productRepository, times(1)).save(product);
        verify(inventoryRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw exception when trying to create product with existing SKU")
    void createProduct_ThrowsExceptionWhenSkuExists() {
        // Arrange
        Product product = new Product();
        product.setName("Coca-Cola 2L");
        product.setSku("COCA-2L-001");
        product.setPrice(BigDecimal.valueOf(10.00));

        when(productRepository.existsBySku("COCA-2L-001")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.createProduct(product)
        );

        assertEquals("Product with SKU COCA-2L-001 already exists.", exception.getMessage());
        verify(productRepository, times(1)).existsBySku("COCA-2L-001");
        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    @DisplayName("Should return product when ID exists")
    void getProductById_Success() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Pepsi 2L");
        product.setSku("PEPSI-2L");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product foundProduct = productService.getProductById(productId);

        // Assert
        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        assertEquals("Pepsi 2L", foundProduct.getName());
    }

    @Test
    @DisplayName("Should throw exception when product ID is not found")
    void getProductById_ThrowsExceptionWhenNotFound() {
        // Arrange
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.getProductById(productId)
        );

        assertEquals("Product not found with id: 99", exception.getMessage());
    }

}