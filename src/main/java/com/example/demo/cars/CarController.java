package com.example.demo.cars;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car")
@Slf4j
@AllArgsConstructor
public class CarController {
    private final CarRepository carRepository;
    private final CarService carService;

    @GetMapping("/")
    public List<Car> getCars(){
        return carRepository.findAll();
    }

    @PostMapping("/")
    public Car addCar(@RequestBody Car car){
        log.info("adding car {}", car.toString());
        return carService.addCar(car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id){
        carRepository.deleteById(id);
    }

    @Transactional
    @PutMapping("/")
    public void editCar(@RequestBody Car car) {
        Optional<Car> c = carRepository.findById(car.getId());
        if (c.isPresent()) {
            c.get().setBrand(car.getBrand());
            c.get().setPlateNumber(car.getPlateNumber());
            c.get().setDrivenDistanceInKm(car.getDrivenDistanceInKm());
            c.get().setCarType(car.getCarType());
            carRepository.save(c.get());
        }
        else log.info("the given id does not exist");

    }

}
