package com.adfer.parkingmanager.controllers;

import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Adrian_Ferenc on 8/8/2016.
 */
@RestController
public class CarController {

  @Autowired
  private CarService carService;

  @RequestMapping(value = "/cars/", method = RequestMethod.POST)
  public ResponseEntity<Void> addNewCar(@RequestBody Car car) {
    carService.addCar(car);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/cars/", method = RequestMethod.GET)
  public List<Car> getCars() {
    return carService.getAllCars();
  }

  @RequestMapping(value = "/cars/{carId}", method = RequestMethod.GET)
  public Car getCar(@PathVariable Long carId) {
    return carService.getCar(carId);
  }

}
