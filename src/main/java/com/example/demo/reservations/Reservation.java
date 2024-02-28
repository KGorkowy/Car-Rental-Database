package com.example.demo.reservations;


import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long customerId;
    private Long carId;
    private String startDate;
    private String endDate;
}
