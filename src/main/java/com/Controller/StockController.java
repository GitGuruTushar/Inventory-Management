package com.Controller;

import com.entity.Stock;
import com.Exception.ResourceNotFoundException;
import com.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        return ResponseEntity.ok(stock);
    }

    @PostMapping
    public Stock createStock(@RequestBody Stock stock) {
        return stockRepository.save(stock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(
        @PathVariable Long id, @RequestBody Stock stockDetails) {

        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));

        stock.setStoreId(stockDetails.getStoreId());
        stock.setQuantityInStock(stockDetails.getQuantityInStock());
        stock.setLastUpdated(stockDetails.getLastUpdated());
        stock.setProduct(stockDetails.getProduct());

        return ResponseEntity.ok(stockRepository.save(stock));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        stockRepository.delete(stock);
        return ResponseEntity.noContent().build();
    }
}
