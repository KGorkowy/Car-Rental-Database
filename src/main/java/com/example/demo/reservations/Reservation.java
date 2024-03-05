package com.example.demo.reservations;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Table
@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "customer id is mandatory")
    @Column(nullable = false, length = 10)
    private Long customerId;
    @NotBlank(message = "car id is mandatory")
    @Column(nullable = false, length = 25)
    private Long carId;
    //@Pattern(regexp = "", message = "Date has a wrong format. It should be in a YYYY-MM-DD format.") difficulties with regexp
    @NotBlank(message = "start date is mandatory")
    @Column(nullable = false, length = 15)
    private String startDate;
    //@Pattern(regexp = "", message = "Date has a wrong format. It should be in a YYYY-MM-DD format.") difficulties with regexp
    @NotBlank(message = "start date is mandatory")
    @Column(nullable = false, length = 15)
    private String endDate;
}
