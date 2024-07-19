package com.example.demo.Service;

// FoodService.java
import com.example.demo.DAO.FoodRepository;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CategoryService categoryService;

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }


        public Food saveFood (Food food){
            String categoryName = food.getCategory().getName();
            Category category = categoryService.findByName(categoryName).get();
            if (category != null) {
                food.setCategory(category);
                return foodRepository.save(food);
            }
            throw new RuntimeException("Category not found with Name: " + categoryName);
        }


    public List<Food> findByCategoryName(String categoryName) {
        return foodRepository.findAllByCategoryName(categoryName);
    }

    public Optional<Food> findById(Long id) {
        return foodRepository.findById(id);
    }



    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }

}
