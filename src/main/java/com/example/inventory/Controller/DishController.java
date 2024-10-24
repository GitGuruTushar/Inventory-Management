package com.example.inventory.Controller;

import com.example.inventory.Exception.ResourceNotFoundException;
import com.example.inventory.entity.Dish;
import com.example.inventory.repository.DishRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishRepository dishRepository;

    // Get all dishes
    @GetMapping
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    // Get dish by ID
    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable Long id) {
        Dish dish = dishRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dish not found with id: " + id));
        return ResponseEntity.ok(dish);
    }

    // Create new dish
    @PostMapping
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) {
        Dish savedDish = dishRepository.save(dish);
        return new ResponseEntity<>(savedDish, HttpStatus.CREATED);
    }

    // Update dish (full update)
    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable Long id, @RequestBody Dish dishDetails) {
        Dish existingDish = dishRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dish not found with id: " + id));

        existingDish.setDishName(dishDetails.getDishName());
        existingDish.setdishDescription(dishDetails.getdishDescription());
        existingDish.setPrice(dishDetails.getPrice());

        Dish updatedDish = dishRepository.save(existingDish);
        return new ResponseEntity<>(updatedDish, HttpStatus.OK);
    }

    // Patch dish (partial update)
    @PatchMapping("/{id}")
    public ResponseEntity<Dish> patchDish(@PathVariable Long id, @RequestBody Dish dishDetails) {
        return dishRepository.findById(id)
            .map(existingDish -> {
                BeanUtils.copyProperties(dishDetails, existingDish, getNullPropertyNames(dishDetails));
                Dish updatedDish = dishRepository.save(existingDish);
                return new ResponseEntity<>(updatedDish, HttpStatus.OK);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Dish not found with id: " + id));
    }

    // Delete dish
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDish(@PathVariable Long id) {
        Dish dish = dishRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dish not found with id: " + id));
        dishRepository.delete(dish);
        return new ResponseEntity<>("Dish deleted successfully", HttpStatus.OK);
    }

    // Utility method for patch
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
