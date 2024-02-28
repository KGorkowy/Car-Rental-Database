package com.example.demo.reservations;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/all")
    public List<Reservation> getCars(){
        return reservationRepository.findAll();
    }

    @PostMapping("/new")
    @Transactional
    public Reservation addReservation(@RequestBody Reservation reservation)
    {
        // to do: one customer can have one car simultaneously, one car can't be reserved twice at the same time,
        // start time can only be earlier than end time - learn time formatting
        log.info("adding reservation {}", reservation.toString());
        return reservationRepository.save(reservation);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable Long id){
        reservationRepository.deleteById(id);
    }

    @PutMapping("/edit")
    @Transactional
    public void editReservation(@RequestBody Reservation reservation)
    {
        Optional<Reservation> res = reservationRepository.findById(reservation.getId());
        if (res.isPresent())
        {
            res.get().setCarId(reservation.getCarId());
            res.get().setCustomerId(reservation.getCustomerId());
            res.get().setStartDate(reservation.getStartDate());
            res.get().setEndDate(reservation.getEndDate());
        }
        else log.info("the given id does not exist");
    }
}
