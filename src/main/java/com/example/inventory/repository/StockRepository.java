package com.example.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
