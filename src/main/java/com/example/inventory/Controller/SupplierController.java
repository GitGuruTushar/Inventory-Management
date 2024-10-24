package com.example.inventory.Controller;

import com.example.inventory.Exception.ResourceNotFoundException;
import com.example.inventory.entity.Supplier;
import com.example.inventory.repository.SupplierRepository;

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
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    // Get all suppliers
    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    // Get supplier by ID
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return ResponseEntity.ok(supplier);
    }

    // Create new supplier
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        Supplier savedSupplier = supplierRepository.save(supplier);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }

    // Update supplier (full update)
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplierDetails) {
        Supplier existingSupplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));

        existingSupplier.setSupplierName(supplierDetails.getSupplierName());
        existingSupplier.setContactInfo(supplierDetails.getContactInfo());
        existingSupplier.setAddress(supplierDetails.getAddress());

        Supplier updatedSupplier = supplierRepository.save(existingSupplier);
        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    // Patch supplier (partial update)
    @PatchMapping("/{id}")
    public ResponseEntity<Supplier> patchSupplier(@PathVariable Long id, @RequestBody Supplier supplierDetails) {
        return supplierRepository.findById(id)
            .map(existingSupplier -> {
                BeanUtils.copyProperties(supplierDetails, existingSupplier, getNullPropertyNames(supplierDetails));
                Supplier updatedSupplier = supplierRepository.save(existingSupplier);
                return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
    }

    // Delete supplier
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        supplierRepository.delete(supplier);
        return new ResponseEntity<>("Supplier deleted successfully", HttpStatus.OK);
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
