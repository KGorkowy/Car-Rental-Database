package com.example.demo.cars;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CarService {
    private final CarRepository carRepository;

    @Transactional
    public Car addCar(Car car) {
        Set<String> plates = carRepository.findAll()
                .stream()
                .map(Car::getPlateNumber)
                .collect(Collectors.toSet());

        if (plates.contains(car.getPlateNumber())) {
            throw new RuntimeException("plate already exists");
        }

        return carRepository.save(car);
    }
}
