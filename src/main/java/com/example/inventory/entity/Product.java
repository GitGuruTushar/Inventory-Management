package com.example.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import java.util.List;



@Entity
@Table(name = "Product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String productDescription;
    private Double unitPrice;
    private Integer reorderLevel;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToMany
    @JoinTable(
        name = "Product_Supplier",  // Define the join table name
        joinColumns = @JoinColumn(name = "productId"),  // Join column from the Product table
        inverseJoinColumns = @JoinColumn(name = "supplierId")  // Join column from the Supplier table
    )
    private List<Supplier> supplier;

    @ManyToOne
    @JoinColumn(name = "recipeID")  // Explicitly reference the correct column in the Recipe table
    private Recipe recipe;

    @OneToOne(mappedBy = "product")  // Bidirectional reference, Stock owns the relationship
    private Stock stock;
    // Constructors
    public Product() {
    }

    public Product(String productName, String productDescription, Double unitPrice, Integer reorderLevel, Category category, List<Supplier> supplier) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.unitPrice = unitPrice;
        this.reorderLevel = reorderLevel;
        this.category = category;
        this.supplier = supplier;
 
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    // ... other getters and setters

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter and Setter for productDescription
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    // Getter and Setter for unitPrice
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    // Getter and Setter for reorderLevel
    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    // Getter and Setter for category
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Getter and Setter for supplier
    public List<Supplier> getSupplier() {
        return supplier;
    }

    public void setSupplier(List<Supplier> supplier) {
        this.supplier = supplier;
    }

   
    // ... other setters
}
