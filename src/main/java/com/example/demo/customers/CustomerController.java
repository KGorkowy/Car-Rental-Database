package com.example.demo.customers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/all")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("/")
    @Transactional
    public ResponseEntity editCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.findById(customer.getId())
                .map(customer1 -> {
                    customer1.setFirstName(customer.getFirstName());
                    customer1.setSurname(customer.getSurname());
                    customer1.setPhoneNumber(customer.getPhoneNumber());
                    //save
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/discount")
    @Transactional
    public ResponseEntity<Void> changeDiscount(@RequestParam Long id, @RequestParam double discount) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customer.get().setDiscountPercentage(discount);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
// TODO: streams, optionals, services, try-catch