package com.example.demo.Controller;

import com.example.demo.Entity.Order;
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
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestParam Long userId) {
        logger.info("Received order: {}", order);
        Order savedOrder = orderService.saveOrder(order, userId);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/table/{tableNumber}/{userId}")
    public ResponseEntity<List<Order>> getOrdersByTable(@PathVariable String tableNumber, @PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByTableNumber(tableNumber, userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/delivery/{userId}")
    public ResponseEntity<List<Order>> getDeliveryOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getDeliveryOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/table/{userId}")
    public ResponseEntity<List<Order>> getTableOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getTableOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestBody Boolean status, @RequestParam Long userId) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status, userId);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}/{userId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId, @PathVariable Long userId) {
        orderService.deleteOrder(orderId, userId);
        return ResponseEntity.noContent().build();
    }
}
