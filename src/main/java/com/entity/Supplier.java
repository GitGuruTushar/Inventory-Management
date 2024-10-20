package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String supplierName;
    private String contactInfo;
    private String address;

    // Constructors
    public Supplier() {
    }

    public Supplier(String supplierName, String contactInfo, String address) {
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.address = address;
    }

    // Getters and Setters
    public Long getSupplierId() {
        return supplierId;
    }

    // ... other getters and setters

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    // Getter and Setter for contactInfo
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Getter and Setter for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    // ... other setters
}
