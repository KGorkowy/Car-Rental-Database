package com.example.demo.cars;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@Slf4j
@AllArgsConstructor
public class CarController {
    private final CarRepository carRepository;
    private final CarService carService;

    @GetMapping("/all")
    public List<Car> getCars(){
        return carRepository.findAll();
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car car) {
        try {
            log.info("adding car {}", car.toString());
            return ResponseEntity.ok(carService.addCar(car));
        }
        catch(Throwable t){
            return ResponseEntity.badRequest().body(t.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id){
        carRepository.deleteById(id);
    }

    @Transactional
    @PutMapping("/")
    public ResponseEntity editCustomer(@Valid @RequestBody Car car) {
        if (carRepository.findById(car.getId()).isPresent()) {
            if (!carRepository.findById(car.getId()).get().getPlateNumber().equals(car.getPlateNumber())) {
                if (carRepository.checkCarPlatesUniqueness(car.getPlateNumber()) != 0) {
                    return ResponseEntity.badRequest().body("number plate is not unique");
                }
                else {
                    return carService.editCar(car);
                }
            }
            else {
                return carService.editCar(car);
            }
        }
        else {
            return ResponseEntity.badRequest().body("given car id does not exist");
        }

    }

}
