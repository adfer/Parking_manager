package com.adfer.parkingmanager.controllers;

import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Adrian_Ferenc on 8/8/2016.
 */
@RestController
public class ParkingController {

  @Autowired
  private ParkingService parkingService;

  @RequestMapping(value = "/parkings/", method = RequestMethod.POST)
  public ResponseEntity<Void> addParking(@RequestBody Parking parking) {
    parkingService.createParking(parking);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/parkings/{parkingId}", method = RequestMethod.GET)
  public ResponseEntity<Parking> getParking(@PathVariable Long parkingId) {
    Parking parking = parkingService.getParking(parkingId);
    return new ResponseEntity<>(parking, HttpStatus.OK);
  }

}
