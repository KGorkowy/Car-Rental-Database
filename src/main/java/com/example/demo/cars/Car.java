package com.example.demo.cars;

import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity
@Data
public class Car {

    private String plateNumber;
    private String brand;
    private Integer drivenDistanceInKm;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



}
