package com.example.demo.reservations;

import com.example.demo.cars.Car;
import com.example.demo.customers.Customer;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Pricing {

    double calculatePrice(LocalDate start , LocalDate end, Customer customer, Car car){
        var diff = DAYS.between(start, end);;

        float basicPrice = switch (car.getCarType()){
            case "hatchback":
                yield 80;
            case "sedan":
                yield 90;
            case "coupe":
                yield 100;
            default:
                throw new IllegalStateException("Unexpected value: " + car.getCarType());
        };
        double lengthDiscount = 1.0;
        if (diff > 7) {
            lengthDiscount = 0.8;
        }
        double customerDiscount = 1.0 - customer.getDiscountPercentage()/100;

        return (diff * basicPrice) * lengthDiscount * customerDiscount;
    }
}

