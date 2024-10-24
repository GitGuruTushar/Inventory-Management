package com.example.inventory.Controller;

import com.example.inventory.Exception.ResourceNotFoundException;
import com.example.inventory.entity.Stock;
import com.example.inventory.repository.StockRepository;

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
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    // Get all stocks
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    // Get stock by ID
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));
        return ResponseEntity.ok(stock);
    }

    // Create new stock
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock savedStock = stockRepository.save(stock);
        return new ResponseEntity<>(savedStock, HttpStatus.CREATED);
    }

    // Update stock (full update)
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stockDetails) {
        Stock existingStock = stockRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));

        existingStock.setQuantityInStock(stockDetails.getQuantityInStock());
        existingStock.setLastUpdated(stockDetails.getLastUpdated());
        existingStock.setProduct(stockDetails.getProduct());

        Stock updatedStock = stockRepository.save(existingStock);
        return new ResponseEntity<>(updatedStock, HttpStatus.OK);
    }

    // Patch stock (partial update)
    @PatchMapping("/{id}")
    public ResponseEntity<Stock> patchStock(@PathVariable Long id, @RequestBody Stock stockDetails) {
        return stockRepository.findById(id)
            .map(existingStock -> {
                BeanUtils.copyProperties(stockDetails, existingStock, getNullPropertyNames(stockDetails));
                Stock updatedStock = stockRepository.save(existingStock);
                return new ResponseEntity<>(updatedStock, HttpStatus.OK);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));
    }

    // Delete stock
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable Long id) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));
        stockRepository.delete(stock);
        return new ResponseEntity<>("Stock deleted successfully", HttpStatus.OK);
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
