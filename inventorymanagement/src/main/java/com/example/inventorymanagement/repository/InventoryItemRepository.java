package com.example.inventorymanagement.repository;

import com.example.inventorymanagement.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
}
