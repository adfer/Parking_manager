package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public void addCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public Car getCar(Long carId) {
        if(carId==null){
            return null;
        }
        return carRepository.findOne(carId);
    }

    @Override
    public void removeCar(Car car) {
        carRepository.delete(car);
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        carRepository.findAll().iterator().forEachRemaining(car -> cars.add(car));
        return cars;
    }

    @Override
    public Car updateCar(Car car) {
        Car persCar = getCar(car.getCarId());
        if(persCar==null){
            return null;
        }
        return carRepository.save(car);
    }
}
