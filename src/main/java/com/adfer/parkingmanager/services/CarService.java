package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.domain.Car;

import java.util.List;

/**
 * Created by adrianferenc on 07.08.2016.
 */
public interface CarService {
    void addCar(Car car);

    Car getCar(Long carId);

    void removeCar(Car car1);

    List<Car> getAllCars();
}
