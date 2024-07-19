package com.example.demo.DAO;


import com.example.demo.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTableNumber(String tableNumber);
    List<Order> findByOrderType(String orderType);
}
