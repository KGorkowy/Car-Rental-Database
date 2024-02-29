package com.example.demo.customers;

import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String surname;
    private String phoneNumber; // int?
}
