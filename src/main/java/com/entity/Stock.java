package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.util.Date;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    private Long storeId;
    private Integer quantityInStock;
    private Date lastUpdated;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Constructors
    public Stock() {
    }

    public Stock(Long storeId, Integer quantityInStock, Date lastUpdated, Product product) {
        this.storeId = storeId;
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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
