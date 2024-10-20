package com.Controller;

import com.entity.Recipe;
import com.Exception.ResourceNotFoundException;
import com.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(
        @PathVariable Long id, @RequestBody Recipe recipeDetails) {

        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

        recipe.setQuantityRequired(recipeDetails.getQuantityRequired());
        recipe.setDish(recipeDetails.getDish());
        recipe.setProduct(recipeDetails.getProduct());

        return ResponseEntity.ok(recipeRepository.save(recipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
        recipeRepository.delete(recipe);
        return ResponseEntity.noContent().build();
    }
}
