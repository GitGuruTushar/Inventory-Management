package com.example.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
