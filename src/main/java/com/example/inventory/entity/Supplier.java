package com.example.inventory.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Supplier")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "supplierId")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;
    
    @Column(nullable = false)
    private String supplierName;
    private String supplierAddress;
    private String contactInfo;

    @ManyToMany(mappedBy = "supplier")
    private List<Product> product = new ArrayList<>();

    // Default constructor
    public Supplier() {
    }

    // Parameterized constructor
    public Supplier(String supplierName, String contactInfo, String supplierAddress, List<Product> product) {
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.supplierAddress = supplierAddress;
        this.product = product;
    }

    // Getters and setters
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return supplierAddress;
    }

    public void setAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}