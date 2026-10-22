package com.erp.erp_system.service;

import com.erp.erp_system.inventory.model.Inventory;
import com.erp.erp_system.inventory.service.InventoryService;
import com.erp.erp_system.order.dto.OrderItemRequest;
import com.erp.erp_system.order.dto.OrderRequest;
import com.erp.erp_system.order.model.Order;
import com.erp.erp_system.order.repository.OrderRepository;
import com.erp.erp_system.order.service.OrderService;
import com.erp.erp_system.product.model.Product;
import com.erp.erp_system.product.service.ProductService;
import com.erp.erp_system.security.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private OrderService orderService;

    private User testUser;
    private OrderRequest orderRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("vitor.fagundes");

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setProductId(100L);
        itemRequest.setQuantity(2);

        orderRequest = new OrderRequest();
        orderRequest.setItems(List.of(itemRequest));
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Cenário do Produto
        Product mockProduct = new Product();
        mockProduct.setId(100L);
        mockProduct.setName("Produto Teste");
        mockProduct.setSku("SKU-TEST-123");
        mockProduct.setPrice(BigDecimal.valueOf(75.00));

        when(productService.getProductById(100L)).thenReturn(mockProduct);

        // Cenário do Inventário (como removeStock retorna um objeto Inventory, usamos when/thenReturn)
        Inventory mockInventory = new Inventory();
        when(inventoryService.removeStock(anyLong(), anyInt())).thenReturn(mockInventory);

        // Cenário do Pedido Salvo
        Order savedOrder = new Order();
        savedOrder.setId(10L);
        savedOrder.setTotalAmount(BigDecimal.valueOf(150.00));

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Ação
        Order result = orderService.createOrder(orderRequest, testUser);

        // Verificação
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(BigDecimal.valueOf(150.00), result.getTotalAmount());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(inventoryService, times(1)).removeStock(eq(100L), eq(2));
    }
}