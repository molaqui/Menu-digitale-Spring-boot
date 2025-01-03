package com.example.demo.Service;

import com.example.demo.DAO.FoodRepository;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.Food;
import com.example.demo.Entity.User;
import com.example.demo.DAO.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public List<Food> getAllFoods(Long userId) {
        return foodRepository.findByUserId(userId);
    }

    public Food saveFood(Food food, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            String categoryName = food.getCategory().getName();
            Category category = categoryService.findByName(categoryName, userId).orElseThrow(() -> new RuntimeException("Category not found with Name: " + categoryName));
            food.setCategory(category);
            food.setUser(userOptional.get());
            return foodRepository.save(food);
        }
        throw new RuntimeException("User not found");
    }

    public List<Food> findByCategoryName(String categoryName, Long userId) {
        return foodRepository.findAllByCategoryNameAndUserId(categoryName, userId);
    }

    public Optional<Food> findById(Long id, Long userId) {
        return foodRepository.findByIdAndUserId(id, userId);
    }

    public void deleteFood(Long id, Long userId) {
        Optional<Food> foodOptional = foodRepository.findByIdAndUserId(id, userId);
        if (foodOptional.isPresent()) {
            foodRepository.deleteById(id);
        } else {
            throw new RuntimeException("Food not found or user does not have permission to delete this food");
        }
    }
}
