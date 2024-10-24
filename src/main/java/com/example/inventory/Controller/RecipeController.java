package com.example.inventory.Controller;

import com.example.inventory.Exception.ResourceNotFoundException;
import com.example.inventory.entity.Recipe;
import com.example.inventory.repository.RecipeRepository;

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
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    // Get all recipes
    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // Get recipe by ID
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));
        return ResponseEntity.ok(recipe);
    }

    // Create new recipe
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
    }

    // Update recipe (full update)
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipeDetails) {
        Recipe existingRecipe = recipeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));

        existingRecipe.setQuantityRequired(recipeDetails.getQuantityRequired());
        existingRecipe.setDish(recipeDetails.getDish());
        existingRecipe.setProduct(recipeDetails.getProduct());

        Recipe updatedRecipe = recipeRepository.save(existingRecipe);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    // Patch recipe (partial update)
    @PatchMapping("/{id}")
    public ResponseEntity<Recipe> patchRecipe(@PathVariable Long id, @RequestBody Recipe recipeDetails) {
        return recipeRepository.findById(id)
            .map(existingRecipe -> {
                BeanUtils.copyProperties(recipeDetails, existingRecipe, getNullPropertyNames(recipeDetails));
                Recipe updatedRecipe = recipeRepository.save(existingRecipe);
                return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));
    }

    // Delete recipe
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));
        recipeRepository.delete(recipe);
        return new ResponseEntity<>("Recipe deleted successfully", HttpStatus.OK);
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
