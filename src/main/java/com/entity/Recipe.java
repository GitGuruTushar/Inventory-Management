package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;



@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private Integer quantityRequired;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Constructors
    public Recipe() {
    }

    public Recipe(Integer quantityRequired, Dish dish, Product product) {
        this.quantityRequired = quantityRequired;
        this.dish = dish;
        this.product = product;
    }

    // Getters and Setters
    public Long getRecipeId() {
        return recipeId;
    }

    // ... other getters and setters

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(Integer quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    // Getter and Setter for dish
    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
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
