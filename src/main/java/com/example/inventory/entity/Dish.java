package com.example.inventory.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Dish")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "dishId")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dishId;

    private String dishName;
    private String dishDescription;
    private Double price;

    @OneToMany(mappedBy = "dish")
    private List<Recipe> recipes;

    // Constructors
    public Dish() {
    }

    public Dish(String dishName, String dishDescription, Double price) {
        this.dishName = dishName;
        this.dishDescription = dishDescription;
        this.price = price;
    }

    // Getters and Setters
    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getdishDescription() {
        return dishDescription;
    }

    public void setdishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}