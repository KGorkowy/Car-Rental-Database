package com.example.demo.cars;

import jakarta.persistence.*;

@Table
@Entity
public class Car {

    private String plateNumber;
    private String brand;
    private Integer drivenDistanceInKm;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getDrivenDistanceInKm() {
        return drivenDistanceInKm;
    }

    public void setDrivenDistanceInKm(Integer drivenDistanceInKm) {
        this.drivenDistanceInKm = drivenDistanceInKm;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
