package com.example.inventory.Controller;

import com.example.inventory.Exception.ResourceNotFoundException;
import com.example.inventory.entity.Category;
import com.example.inventory.repository.CategoryRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Category Management")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Get all categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id)
            .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    // Create new category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // Update category
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        return categoryRepository.findById(id)
            .map(existingCategory -> {
                existingCategory.setCategoryName(categoryDetails.getCategoryName());

                // Optional: You can add more fields here to be updated

                Category updatedCategory = categoryRepository.save(existingCategory);
                return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    // Patch category (Partial Update)
    @PatchMapping("/{id}")
    public ResponseEntity<Category> patchCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        return categoryRepository.findById(id)
            .map(existingCategory -> {
                BeanUtils.copyProperties(categoryDetails, existingCategory, getNullPropertyNames(categoryDetails));
                Category updatedCategory = categoryRepository.save(existingCategory);
                return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    // Delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
            .map(category -> {
                categoryRepository.delete(category);
                return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    // Utility method for patch
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
