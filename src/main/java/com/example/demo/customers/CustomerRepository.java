package com.example.demo.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value="select count(*) as uniqueness from customer where PHONE_NUMBER = :phoneNumber",
            nativeQuery = true)
    Integer checkPhoneNumberUniqueness(@Param("phoneNumber") String phoneNumber);
}
