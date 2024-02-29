package com.example.demo.customers;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/new")
    @Transactional
    public Customer addCustomer(@RequestBody Customer customer) {
        // phone number must be unique and its length must be equal to 9
        if (customer.getPhoneNumber().length() == 9) {
            List<Customer> list = customerRepository.findAll();
            boolean flag = true;
            for (Customer cust : list) {
                if (cust.getPhoneNumber().equals(customer.getPhoneNumber())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return customerRepository.save(customer);
            } else {
                log.info("phone number {} already exists", customer.getPhoneNumber());
                return customer;
            }
        }
        else {
            log.info("invalid phone number");
            return customer;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer (@PathVariable Long id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("/edit")
    public void editCustomer (@RequestBody Customer customer){
        Optional<Customer> customer1 = customerRepository.findById(customer.getId());
        if (customer1.isPresent()) {
            customer1.get().setFirstName(customer.getFirstName());
            customer1.get().setSurname(customer.getSurname());
            customer1.get().setPhoneNumber(customer.getPhoneNumber());
        }
        else log.info("the given id does not exist");
    }


}
