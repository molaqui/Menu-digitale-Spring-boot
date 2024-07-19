package com.example.demo.DAO;

// FoodRepository.java
import com.example.demo.Entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByCategoryName(String categoryName);

}
