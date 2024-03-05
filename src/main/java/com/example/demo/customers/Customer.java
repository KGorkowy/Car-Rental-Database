package com.example.demo.customers;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Table
@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "first name is mandatory")
    @Column(nullable = false, length = 25)
    private String firstName;
    @NotBlank(message = "Please provide surname")
    @Column(nullable = false, length = 25)
    private String surname;

    @Pattern(regexp = "^[ ]*\\d{9}[ ]*$", message = "Phone has a wrong format. IT should be eg: 789789789")
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @NotBlank(message = "first name is mandatory")
    @Column(length = 3) // 0-100
    private double discountPercentage = 0;
}
