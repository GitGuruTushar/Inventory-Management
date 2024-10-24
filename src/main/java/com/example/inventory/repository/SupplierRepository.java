package com.example.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
