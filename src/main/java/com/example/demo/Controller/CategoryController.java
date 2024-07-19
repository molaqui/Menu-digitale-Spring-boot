package com.example.demo.Controller;

// CategoryController.java
import com.example.demo.DTO.CategoryDTO;
import com.example.demo.Entity.Category;
import com.example.demo.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories().stream()
                .map(category -> {
                    CategoryDTO newCategory = new CategoryDTO();
                    newCategory.setId(category.getId());
                    newCategory.setName(category.getName());
                    newCategory.setImage(Base64.getEncoder().encodeToString(category.getImage()));
                    return newCategory;
                })
                .collect(Collectors.toList());
    }



    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("name") String name) {
        try {
            //
            Category category = new Category();
            category.setName(name);
            category.setImage((file.getBytes()));
            categoryService.saveCategory(category);
            return ResponseEntity.ok("Catégorie et image ajoutées avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'upload de l'image : " + e.getMessage());
        }
    }

    @GetMapping("/names")
    public List<String> getAllCategoryNames() {
        return categoryService.getAllCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

}
