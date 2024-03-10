package com.example.demo.reservations;


import com.example.demo.cars.Car;
import com.example.demo.cars.CarRepository;
import com.example.demo.customers.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    public ReservationController(ReservationRepository reservationRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    @GetMapping("/all")
    public List<Reservation> getCars(){
        return reservationRepository.findAll();
    }

    @PostMapping("/")
    @Transactional
    public Reservation addReservation(@RequestBody Reservation reservation)
    {
        // todo: one customer can have one car simultaneously, one car can't be reserved twice at the same time
        log.info("adding reservation {}", reservation.toString());
        Optional<Car> c = carRepository.findById(reservation.getCar().getId());
        if(c.isPresent()) {
            return reservationRepository.save(reservation);
        }
        return null;
    } // todo: make it like putmapping

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id){
        reservationRepository.deleteById(id);
    }

    @PutMapping("/")
    @Transactional
    public void editReservation(@RequestBody Reservation reservation)
    {

        Optional<Car> c = carRepository.findById(reservation.getCar().getId());
        Optional<Reservation> res = reservationRepository.findById(reservation.getId());
        if (res.isPresent() && c.isPresent())
        {
            res.get().setCar(c.get());
            res.get().setCustomerId(reservation.getCustomerId());
            res.get().setStartDate(reservation.getStartDate());
            res.get().setEndDate(reservation.getEndDate());
        }
        else log.info("the given id does not exist");
    }

    /*@GetMapping("/{id}")
    public double getPricing(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        Optional<Customer> customer = customerRepository.findById(reservation.get().getCustomerId());
        if (reservation.isPresent())
        {
            return Pricing.calculatePrice(reservationRepository.findById(id).get().getStartDate(), reservationRepository.findById(id).get().getEndDate(), customer, car);
        }
        else
            throw new RuntimeException("given reservation doesn't exist");



    }*/

}
