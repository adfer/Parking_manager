package com.adfer.parkingmanager.repository;

import com.adfer.parkingmanager.domain.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Long>{
}
