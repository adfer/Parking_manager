package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.domain.ParkingPermission;
import com.adfer.parkingmanager.repository.ParkingPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by adrianferenc on 08.08.2016.
 */
@Service
public class ParkingPermissionServiceImpl implements ParkingPermissionService {

    private final ParkingPermissionRepository parkingPermissionRepository;

    @Autowired
    public ParkingPermissionServiceImpl(ParkingPermissionRepository parkingPermissionRepository) {
        this.parkingPermissionRepository = parkingPermissionRepository;
    }

    @Override
    public void addPermission(ParkingPermission parkingPermission) {
        parkingPermissionRepository.save(parkingPermission);
    }

    @Override
    public List<Car> getAllowedCars(Parking parking) {
        List<ParkingPermission> parkingPermissions = parkingPermissionRepository.findByParkingId(parking.getId());
        return parkingPermissions.stream().map(parkingPermission -> parkingPermission.getCar()).collect(Collectors.toList());
    }

    @Override
    public ParkingPermission getPermissionForCar(Car car) {
        return parkingPermissionRepository.findByCar(car);
    }

    @Override
    public void removePermission(ParkingPermission parkingPermission) {
        parkingPermissionRepository.delete(parkingPermission);
    }
}
