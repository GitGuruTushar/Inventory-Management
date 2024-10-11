package com.example.inventorymanagement.model;

import lombok.Data;
// import javax.persistence.*;

@Entity
@Data
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contact;
    private String address;
    private String phone;
}
