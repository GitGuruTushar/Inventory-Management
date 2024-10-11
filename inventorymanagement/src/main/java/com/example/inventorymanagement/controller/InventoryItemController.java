package com.example.inventorymanagement.controller;

import com.example.inventorymanagement.model.InventoryItem;
import com.example.inventorymanagement.service.InventoryItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryItemController {

    @Autowired
    private InventoryItemService inventoryItemService;

    @Operation(summary = "Get all inventory items")
    @GetMapping("/items")
    public List<InventoryItem> getAllItems() {
        return inventoryItemService.getAllItems();
    }

    @Operation(summary = "Get an inventory item by ID")
    @GetMapping("/items/{id}")
    public InventoryItem getItemById(@PathVariable Long id) {
        return inventoryItemService.getItemById(id);
    }

    @Operation(summary = "Create a new inventory item")
    @PostMapping("/items")
    public InventoryItem createItem(@RequestBody InventoryItem item) {
        return inventoryItemService.createItem(item);
    }

    @Operation(summary = "Delete an inventory item")
    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        inventoryItemService.deleteItem(id);
    }
}
