package com.example.demo.cars;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
