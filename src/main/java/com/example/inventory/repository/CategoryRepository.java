package com.example.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
