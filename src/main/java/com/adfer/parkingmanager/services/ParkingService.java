package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.domain.ParkingPermission;

import java.util.List;

/**
 * Created by adrianferenc on 07.08.2016.
 */
public interface ParkingService {

    Parking getParking(Long parkingId);

    Parking createParking(Parking parking);

    void removeParking(Parking parkingId);

}
