package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.domain.ParkingPermission;

import java.util.List;

/**
 * Created by adrianferenc on 08.08.2016.
 */
public interface ParkingPermissionService {

    void addPermission(ParkingPermission parkingPermission1);

    void removePermission(ParkingPermission parkingPermission3);

    List<Car> getAllowedCars(Parking parking);

    ParkingPermission getPermissionForCar(Car car);
}
