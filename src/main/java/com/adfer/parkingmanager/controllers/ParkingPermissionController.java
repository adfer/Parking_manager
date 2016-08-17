package com.adfer.parkingmanager.controllers;

import com.adfer.parkingmanager.domain.ParkingPermission;
import com.adfer.parkingmanager.services.ParkingPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by adrianferenc on 17.08.2016.
 */
@RestController
@RequestMapping("/permissions")
public class ParkingPermissionController {

    @Autowired
    private ParkingPermissionService parkingPermissionService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createParkingPermission(@RequestBody ParkingPermission parkingPermission){
        parkingPermissionService.addPermission(parkingPermission);
        return new ResponseEntity(HttpStatus.OK);
    }

}
