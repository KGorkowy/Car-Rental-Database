package com.example.demo.cars;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        List<Car> list = carRepository.findAll(); //creating a list of cars in db
        List<String> listOfCarPlates = new ArrayList<>();

        for (Car car1 : list)
        {
            listOfCarPlates.add(car1.getPlateNumber()); //creating a list of all car plates in db
        }
        boolean flag = true; //checking if the given car plate already exists
        for (String CarPlate : listOfCarPlates)
        {
            if (car.getPlateNumber().equals(CarPlate)) {
                flag = false;
                break;
            }
        }
        if (flag)
        {
            return carRepository.save(car);
        }
        else
        {
            log.info("numberplate {} already exists", car.getPlateNumber());
            return car;
        }

    }

    @DeleteMapping("/delete")
    public void deleteCar(Long id){
        Optional<Car> c = carRepository.findById(id);
        c = Optional.empty();
        
    }

    @Transactional
    @PutMapping("/edit")
    public void editCar(Long id, Car car) {
        Optional<Car> c = carRepository.findById(id);
        if (c.isPresent()) {
            c.get().setBrand(car.getBrand());
            c.get().setPlateNumber(car.getPlateNumber());
            c.get().setDrivenDistanceInKm(car.getDrivenDistanceInKm());
            carRepository.save(c.get());
        }

//        version 2
//        List<Car> list = carRepository.findAll();
//        boolean flag = true; //checking if the given car exists
//        for (Car car1 : list)
//        {
//            if (car1.getPlateNumber().equals(car.getPlateNumber()))
//            {
//                car1.setBrand(car.getBrand());
//                car1.setPlateNumber(car.getPlateNumber());
//                car1.setDrivenDistanceInKm(car.getDrivenDistanceInKm());
//                flag = false;
//            }
//        }
//        if (flag)
//        {
//            log.info("");
//        }

//        version 3



    }


}
