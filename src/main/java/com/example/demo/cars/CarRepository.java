package com.example.demo.cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value="select count(*) as uniqueness from car where PLATE_NUMBER = :carPlate",
            nativeQuery = true)
    Integer checkCarPlatesUniqueness(@Param("carPlate") String carPlate);

}
