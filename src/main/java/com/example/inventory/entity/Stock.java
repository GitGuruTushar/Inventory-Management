package com.example.inventory.entity;

import jakarta.persistence.*;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "Stock")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "stockId")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;
    private Integer quantityInStock;
    private Date lastUpdated;

    @OneToOne
    @JoinColumn(name = "productId")  // Foreign key referencing Product
    private Product product;

    // Default constructor
    public Stock() {
    }

    // Parameterized constructor
    public Stock( Integer quantityInStock, Date lastUpdated, Product product) {

        this.quantityInStock = quantityInStock;
        this.lastUpdated = lastUpdated;
        this.product = product;
    }


    // Getters and Setters
    public Long getStockId() {
        return stockId;
    }

    // ... other getters and setters

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    // Getter and Setter for quantityInStock
    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    // Getter and Setter for lastUpdated
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // Getter and Setter for product
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // ... other setters
}
