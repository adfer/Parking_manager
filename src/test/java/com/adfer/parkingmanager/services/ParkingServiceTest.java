package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.Application;
import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.domain.ParkingPermission;
import com.adfer.parkingmanager.repository.CarRepository;
import com.adfer.parkingmanager.repository.ParkingPermisionRepository;
import com.adfer.parkingmanager.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@SpringBootTest(classes = Application.class)
@Test
public class ParkingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private ParkingPermisionRepository parkingPermisionRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private CarRepository carRepository;

    @AfterMethod
    public void cleanUp(){
        parkingPermisionRepository.deleteAll();
        parkingRepository.deleteAll();
        carRepository.deleteAll();
    }

    public void shouldCreateParking() throws Exception{
        //given
        Parking parking = new Parking();

        //execute
        parkingService.createParking(parking);

        //verify
        Parking persParking = parkingService.getParking(parking.getId());
        assertNotNull(persParking);
    }

    public void shouldReturnPersistentParking() throws Exception {
        //given
        Parking parking = new Parking();
        parkingService.createParking(parking);

        //execute
        Parking persParking = parkingService.getParking(parking.getId());

        //verify
        assertEquals(persParking.getId(), parking.getId());
    }

    public void shouldReturnNull_notExistingParking() {
        //execute
        Parking persParking = parkingService.getParking(-1L);

        //verify
        assertNull(persParking);
    }

    public void shouldRemoveParking() throws Exception{
        //given
        Parking parking = new Parking();
        parkingService.createParking(parking);

        //execute
        parkingService.removeParking(parking);

        //verify
        Parking persParking = parkingService.getParking(parking.getId());
        assertNull(persParking);
    }

    public void shouldCreateParkingPermission() throws Exception {
        //given
        Parking parking = new Parking();
        parkingService.createParking(parking);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        List<Car> expectedAllowedCars = Arrays.asList(car1, car2, car3);
        ParkingPermission parkingPermission1 = new ParkingPermission();
        parkingPermission1.setParkingId(parking.getId());
        parkingPermission1.setCar(car1);
        ParkingPermission parkingPermission2 = new ParkingPermission();
        parkingPermission2.setParkingId(parking.getId());
        parkingPermission2.setCar(car2);
        ParkingPermission parkingPermission3 = new ParkingPermission();
        parkingPermission3.setParkingId(parking.getId());
        parkingPermission3.setCar(car3);

        //execute
        parkingService.addPermission(parkingPermission1);
        parkingService.addPermission(parkingPermission2);
        parkingService.addPermission(parkingPermission3);

        //validate
        List<Car> allowedCars = parkingService.getAllowedCars(parking);
        Comparator<Car> byCarIdAsc = (c1, c2) -> Long.compare(c1.getCarId(), c2.getCarId());
        allowedCars.stream().sorted(byCarIdAsc);
        expectedAllowedCars.stream().sorted(byCarIdAsc);
        assertTrue(allowedCars.size() == 3);
        assertEquals(allowedCars.get(0).getCarId(), expectedAllowedCars.get(0).getCarId());
        assertEquals(allowedCars.get(1).getCarId(), expectedAllowedCars.get(1).getCarId());
        assertEquals(allowedCars.get(2).getCarId(), expectedAllowedCars.get(2).getCarId());

    }

    public void shouldRemoveOneParkingPermission() throws Exception {
        //given
        Parking parking = new Parking();
        parkingService.createParking(parking);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        ParkingPermission parkingPermission1 = new ParkingPermission();
        parkingPermission1.setParkingId(parking.getId());
        parkingPermission1.setCar(car1);
        ParkingPermission parkingPermission2 = new ParkingPermission();
        parkingPermission2.setParkingId(parking.getId());
        parkingPermission2.setCar(car2);
        ParkingPermission parkingPermission3 = new ParkingPermission();
        parkingPermission3.setParkingId(parking.getId());
        parkingPermission3.setCar(car3);
        parkingService.addPermission(parkingPermission1);
        parkingService.addPermission(parkingPermission2);
        parkingService.addPermission(parkingPermission3);

        //execute
        parkingService.removePermission(parkingPermission3);

        //validate
        List<Car> allowedCars = parkingService.getAllowedCars(parking);
        assertTrue(allowedCars.size() == 2);
        assertNotEquals(allowedCars.get(0).getCarId(), car3.getCarId());
        assertNotEquals(allowedCars.get(1).getCarId(), car3.getCarId());

    }

}
