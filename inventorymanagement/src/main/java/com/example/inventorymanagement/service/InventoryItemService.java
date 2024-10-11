package com.example.inventorymanagement.service;

import com.example.inventorymanagement.model.InventoryItem;
import com.example.inventorymanagement.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryItemService {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    public List<InventoryItem> getAllItems() {
        return inventoryItemRepository.findAll();
    }

    public InventoryItem getItemById(Long id) {
        return inventoryItemRepository.findById(id).orElse(null);
    }

    public InventoryItem createItem(InventoryItem item) {
        return inventoryItemRepository.save(item);
    }

    public void deleteItem(Long id) {
        inventoryItemRepository.deleteById(id);
    }
}
