package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.domain.ParkingPermission;
import com.adfer.parkingmanager.repository.CarRepository;
import com.adfer.parkingmanager.repository.ParkingPermisionRepository;
import com.adfer.parkingmanager.repository.ParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@Service
public class ParkingServiceImpl implements ParkingService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private final ParkingRepository parkingRepository;

    private final CarRepository carRepository;

    private final ParkingPermisionRepository parkingPermisionRepository;

    @Autowired
    public ParkingServiceImpl(ParkingRepository parkingRepository, CarRepository carRepository, ParkingPermisionRepository parkingPermisionRepository) {
        this.parkingRepository = parkingRepository;
        this.carRepository = carRepository;
        this.parkingPermisionRepository = parkingPermisionRepository;
    }

    @Override
    public Parking getParking(Long parkingId) {
        return parkingRepository.findOne(parkingId);
    }

    @Override
    public Parking createParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    @Override
    public void removeParking(Parking parking) {
        parkingRepository.delete(parking);
    }

    @Override
    public void addPermission(ParkingPermission parkingPermission) {
        parkingPermisionRepository.save(parkingPermission);
    }

    @Override
    public List<Car> getAllowedCars(Parking parking) {
        List<ParkingPermission> parkingPermissions = parkingPermisionRepository.findByParkingId(parking.getId());
        return parkingPermissions.stream().map(parkingPermission -> parkingPermission.getCar()).collect(Collectors.toList());
    }

    @Override
    public void removePermission(ParkingPermission parkingPermission) {
        parkingPermisionRepository.delete(parkingPermission);
    }
}
