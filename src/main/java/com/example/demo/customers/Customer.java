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
    private String firstName;
    @NotBlank(message = "Please provide surname")
    private String surname;

    @Pattern(regexp = "^[ ]*\\d{9}[ ]*$", message = "Phone has a wrong format. IT should be eg: 789789789")
    private String phoneNumber;
}
