package com.example.demo.reservations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    @Transactional
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Transactional
    public ResponseEntity<?> editReservation(Reservation reservation) {
        return reservationRepository.findById(reservation.getId())
                .map(reservation1 -> {
                    reservation1.setCustomerId(reservation.getCustomerId());
                    reservation1.setCar(reservation.getCar());
                    reservation1.setStartDate(reservation.getStartDate());
                    reservation1.setEndDate(reservation.getEndDate());
                    //save
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
