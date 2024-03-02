package com.example.demo.customers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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
    @Transactional
    public Customer addCustomer(@Valid @RequestBody Customer customer) throws BadRequestException {
        List<Customer> customers = customerRepository.findAll();
        boolean isPhoneNumberUnique = true;
        for (Customer cust : customers) {
            if (cust.getPhoneNumber().equals(customer.getPhoneNumber())) {
                isPhoneNumberUnique = false;
                break;
            }
        }
        if (isPhoneNumberUnique) {
            return customerRepository.save(customer);
        }
        throw new BadRequestException("phone number " + customer.getPhoneNumber() + " already exists");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("/edit")
    public void editCustomer(@Valid @RequestBody Customer customer) {
        Optional<Customer> customer1 = customerRepository.findById(customer.getId());
        if (customer1.isPresent()) {
            customer1.get().setFirstName(customer.getFirstName());
            customer1.get().setSurname(customer.getSurname());
            customer1.get().setPhoneNumber(customer.getPhoneNumber());
        } else log.info("the given id does not exist");
    }


}
