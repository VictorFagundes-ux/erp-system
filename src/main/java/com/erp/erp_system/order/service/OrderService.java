package com.erp.erp_system.order.service;

import com.erp.erp_system.inventory.service.InventoryService;
import com.erp.erp_system.order.model.OrderItem;
import com.erp.erp_system.order.repository.OrderRepository;
import com.erp.erp_system.order.dto.OrderItemRequest;
import com.erp.erp_system.order.dto.OrderRequest;
import com.erp.erp_system.order.model.Order;
import com.erp.erp_system.product.model.Product;
import com.erp.erp_system.product.service.ProductService;
import com.erp.erp_system.security.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final InventoryService inventoryService;

    public OrderService(OrderRepository orderRepository, ProductService productService, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public Order createOrder(OrderRequest request, User currentUser) {
        Order order = new Order();
        order.setUser(currentUser);
        order.setStatus("COMPLETED");

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemReq : request.getItems()) {
            // 1. Busca o produto
            Product product = productService.getProductById(itemReq.getProductId());

            // 2. Remove do estoque (Isso já valida se há saldo suficiente)
            inventoryService.removeStock(product.getId(), itemReq.getQuantity());

            // 3. Cria o item do pedido
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setUnitPrice(product.getPrice());

            orderItems.add(orderItem);

            // 4. Soma ao valor total do pedido (Preço * Quantidade)
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
}

