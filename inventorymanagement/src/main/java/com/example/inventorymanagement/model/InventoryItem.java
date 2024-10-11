package com.example.inventorymanagement.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "inventory_item")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String unit;
    private String category;
    private int reorderLevel;
}
