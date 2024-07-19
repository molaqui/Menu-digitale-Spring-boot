package com.example.demo.Controller;


import com.example.demo.Entity.Order;
import com.example.demo.Enum.Status;
import com.example.demo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        logger.info("Received order: {}", order);
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/table/{tableNumber}")
    public ResponseEntity<List<Order>> getOrdersByTable(@PathVariable String tableNumber) {
        List<Order> orders = orderService.getOrdersByTableNumber(tableNumber);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/delivery")
    public ResponseEntity<List<Order>> getDeliveryOrders() {
        List<Order> orders = orderService.getDeliveryOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/table")
    public ResponseEntity<List<Order>> getTableOrders() {
        List<Order> orders = orderService.getTableOrders();
        return ResponseEntity.ok(orders);
    }
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestBody Boolean status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}