package com.example.demo.customers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public Customer addCustomer(Customer customer) {
        if (customerRepository.checkPhoneNumberUniqueness(customer.getPhoneNumber()) > 0) {
            throw new RuntimeException("phone number already exists");
        }

        return customerRepository.save(customer);
    }

    @Transactional
    public ResponseEntity<?> editCustomer(Customer customer) {
        return customerRepository.findById(customer.getId())
                .map(customer1 -> {
                    customer1.setFirstName(customer.getFirstName());
                    customer1.setSurname(customer.getSurname());
                    customer1.setPhoneNumber(customer.getPhoneNumber());
                    customer1.setDiscountPercentage(0.0); // setting percentage to default, safety reasons
                    //save
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    public ResponseEntity<?> editDiscount(Long id, double discount) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customer.get().setDiscountPercentage(discount);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
