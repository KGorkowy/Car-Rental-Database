package com.example.demo.reservations;


import com.example.demo.cars.CarRepository;
import com.example.demo.customers.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
@Slf4j
@AllArgsConstructor
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final ReservationService reservationService;

    @GetMapping("/all")
    public List<Reservation> getCars(){
        return reservationRepository.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<?> addReservation(@RequestBody Reservation reservation)
    {
        // todo: ask if it should be inverted
        try {
            List<Reservation> listOfAllReservations = reservationRepository.findAll();
            // checking if reservation doesn't have an invalid customer or car
            for (Reservation res : listOfAllReservations) {
                if ((res.getStartDate().isBefore(reservation.getStartDate()) && res.getEndDate().isAfter(reservation.getStartDate())) ||
                        (res.getStartDate().isAfter(reservation.getStartDate()) && res.getEndDate().isBefore(reservation.getEndDate())) ||
                        (res.getStartDate().isBefore(reservation.getEndDate()) && res.getEndDate().isAfter(reservation.getEndDate())) ||
                        res.getStartDate().isEqual(reservation.getStartDate()) || res.getEndDate().isEqual(reservation.getEndDate())) {
                    if (res.getCustomerId().equals(reservation.getCustomerId())) {
                        return ResponseEntity.badRequest().body("given customer already rents a car in given period");
                    }
                    else if (res.getCar().getId().equals(reservation.getCar().getId())) {
                        return ResponseEntity.badRequest().body("given car is already rented in given period");
                    }
                }
            }
            if (reservation.getStartDate().isBefore(reservation.getEndDate())) {
                log.info("adding reservation {}", reservation);
                return ResponseEntity.ok(reservationService.addReservation(reservation));
            }
            else {
                return ResponseEntity.badRequest().body("given period is not valid - start date after end date");
            }
        }
        catch(Throwable t){
            return ResponseEntity.badRequest().body(t.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id){
        reservationRepository.deleteById(id);
    }

    @PutMapping("/")
    @Transactional
    public ResponseEntity<?> editReservation(@RequestBody Reservation reservation)
    {
        try {
            List<Reservation> listOfAllReservations = reservationRepository.findAll();
            // checking if reservation doesn't have an invalid customer or car
            for (Reservation res : listOfAllReservations) {
                if ((res.getStartDate().isBefore(reservation.getStartDate()) && res.getEndDate().isAfter(reservation.getStartDate())) ||
                        (res.getStartDate().isAfter(reservation.getStartDate()) && res.getEndDate().isBefore(reservation.getEndDate())) ||
                        (res.getStartDate().isBefore(reservation.getEndDate()) && res.getEndDate().isAfter(reservation.getEndDate())) ||
                        res.getStartDate().isEqual(reservation.getStartDate()) || res.getEndDate().isEqual(reservation.getEndDate())) {
                    if (res.getCustomerId().equals(reservation.getCustomerId())) {
                        return ResponseEntity.badRequest().body("given customer already rents a car in given period");
                    }
                    else if (res.getCar().getId().equals(reservation.getCar().getId())) {
                        return ResponseEntity.badRequest().body("given car is already rented in given period");
                    }
                }
            }
            if (reservation.getStartDate().isBefore(reservation.getEndDate())) {
                log.info("editing reservation {}", reservation);
                return ResponseEntity.ok(reservationService.editReservation(reservation));
            }
            else {
                return ResponseEntity.badRequest().body("given period is not valid - start date after end date");
            }
        }
        catch(Throwable t){
            return ResponseEntity.badRequest().body(t.getMessage());
        }
    }

    @GetMapping("/{id}")
    public double getPricing(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent())
        {
            return Pricing.calculatePrice(reservationRepository.findById(id).get().getStartDate(), reservationRepository.findById(id).get().getEndDate(),
                    customerRepository.findById(reservation.get().getCustomerId()).get(), carRepository.findById(reservation.get().getCustomerId()).get());
        }
        else
            throw new RuntimeException("given reservation doesn't exist");
    }

}
