package com.adfer.parkingmanager.repository;

import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.domain.Parking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@Repository
public interface ParkingRepository extends CrudRepository<Parking, Long> {

}
