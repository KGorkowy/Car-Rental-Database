package com.example.demo.cars;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CarService {
    private final CarRepository carRepository;

    @Transactional
    public Car addCar(Car car) {
        if (carRepository.checkCarPlatesUniqueness(car.getPlateNumber()) > 0) {
            throw new RuntimeException("plate already exists");
        }

        return carRepository.save(car);
    }

    @Transactional
    public ResponseEntity editCar(Car newCar) {
        return carRepository.findById(newCar.getId())
                .map(car1 -> {
                    car1.setBrand(newCar.getBrand());
                    car1.setCarType(newCar.getBrand());
                    car1.setPlateNumber(newCar.getPlateNumber());
                    car1.setDrivenDistanceInKm(newCar.getDrivenDistanceInKm());
                    //save
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
