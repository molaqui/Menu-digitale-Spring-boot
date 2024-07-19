package com.example.demo.Controller;

// FoodController.java
import com.example.demo.Entity.Category;
import com.example.demo.Entity.Food;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private CategoryService categoryService;



    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    @PostMapping
    public ResponseEntity<String> addFood(
            @RequestParam("image") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("categoryName") String categoryName) {
        try {
            Category category = categoryService.findByName(categoryName)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Food food = new Food();
            food.setName(name);
            food.setPrice(price);
            food.setDescription(description);
            food.setImage(file.getBytes());
            food.setCategory(category);

            foodService.saveFood(food);
            return ResponseEntity.ok("Food added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding food: " + e.getMessage());
        }
    }

    @GetMapping("/by-category/{categoryName}")
    public ResponseEntity<List<Food>> getFoodsByCategory(@PathVariable String categoryName) {
        try {
            List<Food> foods = foodService.findByCategoryName(categoryName);
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ArrayList<>());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFood(
            @PathVariable Long id,
            @RequestParam("image") Optional<MultipartFile> file,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("categoryName") String categoryName) {
        try {
            Category category = categoryService.findByName(categoryName)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            Food food = foodService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            food.setName(name);
            food.setPrice(price);
            food.setDescription(description);
            if (file.isPresent()) {
                food.setImage(file.get().getBytes());
            }
            food.setCategory(category);

            foodService.saveFood(food);
            return ResponseEntity.ok("Food updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating food: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id) {
        try {
            foodService.deleteFood(id);
            return ResponseEntity.ok("Food deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting food: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        try {
            Optional<Food> food = foodService.findById(id);
            if (food.isPresent()) {
                return ResponseEntity.ok(food.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
