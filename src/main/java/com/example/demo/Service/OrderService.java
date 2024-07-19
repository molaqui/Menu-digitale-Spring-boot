package com.example.demo.Service;



import com.example.demo.DAO.OrderRepository;
import com.example.demo.Entity.Order;

import com.example.demo.Entity.OrderItem;
import com.example.demo.Enum.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        double total = 0;
        for (OrderItem item : order.getItems()) {
            item.setOrder(order);
            total += item.getPrice() * item.getQuantity();
        }
        order.setTotal(total);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByTableNumber(String tableNumber) {
        return orderRepository.findByTableNumber(tableNumber);
    }

    public List<Order> getDeliveryOrders() {
        return orderRepository.findByOrderType("delivery");
    }

    public List<Order> getTableOrders() {
        return orderRepository.findByOrderType("table");
    }

    public Order updateOrderStatus(Long orderId, Boolean status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
