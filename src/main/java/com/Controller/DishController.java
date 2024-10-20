package com.Controller;

import com.entity.Dish;
import com.Exception.ResourceNotFoundException;
import com.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishRepository dishRepository;

    @GetMapping
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable Long id) {
        Dish dish = dishRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dish not found"));
        return ResponseEntity.ok(dish);
    }

    @PostMapping
    public Dish createDish(@RequestBody Dish dish) {
        return dishRepository.save(dish);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(
        @PathVariable Long id, @RequestBody Dish dishDetails) {

        Dish dish = dishRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dish not found"));

        dish.setDishName(dishDetails.getDishName());
        dish.setDescription(dishDetails.getDescription());
        dish.setPrice(dishDetails.getPrice());

        return ResponseEntity.ok(dishRepository.save(dish));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        Dish dish = dishRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dish not found"));
        dishRepository.delete(dish);
        return ResponseEntity.noContent().build();
    }
}
