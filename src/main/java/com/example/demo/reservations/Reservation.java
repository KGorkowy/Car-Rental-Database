package com.example.demo.reservations;


import com.example.demo.cars.Car;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Table
@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // @NotBlank(message = "customer id is mandatory")
    @Column(nullable = false, length = 10)
    private Long customerId;
    // @NotBlank(message = "car id is mandatory")
    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;
    //@Pattern(regexp = "", message = "Date has a wrong format. It should be in a YYYY-MM-DD format.") difficulties with regexp
    // @NotBlank(message = "start date is mandatory")
    @Column(nullable = false, length = 15)
    private LocalDate startDate;
    //@Pattern(regexp = "", message = "Date has a wrong format. It should be in a YYYY-MM-DD format.") difficulties with regexp
    // @NotBlank(message = "start date is mandatory")
    @Column(nullable = false, length = 15)
    private LocalDate endDate;
}
