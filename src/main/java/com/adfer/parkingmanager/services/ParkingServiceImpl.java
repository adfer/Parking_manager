package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
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

}
