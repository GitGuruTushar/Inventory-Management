package com.example.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
