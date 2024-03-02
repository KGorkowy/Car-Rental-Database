package com.example.demo.reservations;

import com.example.demo.customers.Customer;

import java.time.Instant;

public class Pricing {

    Integer calculatePrice(Instant start , Instant end, Customer customer){
        var diff = (end.toEpochMilli() - start.toEpochMilli())/1000;
        //Todo: find a way to calculate time between start and end, find a pricing model, after 10th day you get a discount, user may have a discount ie. 10%
    }

    void dd(){
        calculatePrice(Instant.parse("2024-01-01T00.00.00Z"),Instant.now(), null);
    }
}
