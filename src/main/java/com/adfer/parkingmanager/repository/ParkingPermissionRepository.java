package com.adfer.parkingmanager.repository;

import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.domain.ParkingPermission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@Repository
public interface ParkingPermissionRepository extends CrudRepository<ParkingPermission, Long>{
    List<ParkingPermission> findByParkingId(Long id);

    ParkingPermission findByCar(Car car);
}
