package com.example.demo.customers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@Slf4j
@AllArgsConstructor
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @GetMapping("/all")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            log.info("adding customer {}", customer.toString());
            return ResponseEntity.ok(customerService.addCustomer(customer));
        }
        catch(Throwable t){
            return ResponseEntity.badRequest().body(t.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("/")
    public ResponseEntity<?> editCustomer(@Valid @RequestBody Customer customer) {
        if (customerRepository.findById(customer.getId()).isPresent()) {
            if (!customerRepository.findById(customer.getId()).get().getPhoneNumber().equals(customer.getPhoneNumber())) {
                if (customerRepository.checkPhoneNumberUniqueness(customer.getPhoneNumber()) != 0) {
                    return ResponseEntity.badRequest().body("phone number is not unique");
                }
                else {
                    log.info("editing customer with id {}", customer.getId());
                    return customerService.editCustomer(customer);
                }
            }
            else {
                log.info("editing customer with id {}", customer.getId());
                return customerService.editCustomer(customer);
            }
        }
        else {
            return ResponseEntity.badRequest().body("given customer id does not exist");
        }
    }


    @PutMapping("/discount")
    public ResponseEntity<?> changeDiscount(@RequestParam Long id, @RequestParam double discount) {
        try {
            log.info("changing discount of customer with id {}", id);
            return ResponseEntity.ok(customerService.editDiscount(id, discount));
        }
        catch(Throwable t){
            return ResponseEntity.badRequest().body(t.getMessage());
        }
    }

}
// TODO: streams, optionals, services, try-catch