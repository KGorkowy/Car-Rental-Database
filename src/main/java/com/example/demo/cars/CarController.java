package com.example.demo.cars;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {
    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/all")
    public List<Car> getCars(){
        return carRepository.findAll();
    }

    @PostMapping
    @Transactional
    public Car addCar(@RequestBody Car car){
        log.info("adding car {}", car.toString());
        // CarPlate's validation for uniqueness
        return carRepository.save(car);
    }

    @DeleteMapping
    public void deleteCare(Long id){
//        Optional<Car> c = carRepository.findById(id);
//        if(c.isPresent()){
//            c.get().setBrand("Ford");
//            carRepository.save(c.get())
//        }

    }
//    @Transactional
    //@PutMapping // edit


}
