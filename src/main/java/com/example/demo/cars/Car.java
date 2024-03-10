package com.example.demo.cars;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Table
@Entity
@Data
public class Car {

    @NotBlank(message = "plate number is mandatory")
    @Column(nullable = false, length = 9)
    private String plateNumber;
    @NotBlank(message = "brand name is mandatory")
    @Column(nullable = false, length = 25)
    private String brand;
    @NotNull(message = "driven distance is mandatory")
    @Column(nullable = false, length = 10)
    private Integer drivenDistanceInKm;
    @NotBlank(message = "car type is mandatory")
    @Column(nullable = false, length = 15)
    private String carType;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
