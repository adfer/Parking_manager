package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.Application;
import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.domain.ParkingPermission;
import com.adfer.parkingmanager.repository.CarRepository;
import com.adfer.parkingmanager.repository.ParkingPermissionRepository;
import com.adfer.parkingmanager.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by adrianferenc on 08.08.2016.
 */
@SpringBootTest(classes = Application.class)
@Test
public class ParkingPermissionServiceTest extends AbstractTestNGSpringContextTests {


    @Autowired
    private ParkingPermissionService parkingPermissionService;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ParkingPermissionRepository parkingPermissionRepository;

    @AfterMethod
    public void cleanUp() {
        parkingPermissionRepository.deleteAll();
        carRepository.deleteAll();
        parkingRepository.deleteAll();
    }

    public void shouldCreateParkingPermissionWithSpecifiedPeriod() throws Exception {
        //given
        Parking parking = new Parking();
        parkingRepository.save(parking);
        Car car = new Car();
        carRepository.save(car);
        ParkingPermission parkingPermission =
                new ParkingPermission.Builder(parking.getId(), car)
                        .validFrom(LocalDateTime.of(2000, 1, 1, 18, 0))
                        .validUntil(LocalDateTime.of(2020, 1, 1, 0, 0))
                        .build();

        //execute
        parkingPermissionService.addPermission(parkingPermission);

        //validate
        ParkingPermission persParkingPermission = parkingPermissionService.getPermissionForCar(car);
        assertEquals(persParkingPermission.getCar().getCarId(), car.getCarId());
        assertEquals(persParkingPermission.getParkingId(), parking.getId());
        assertEquals(persParkingPermission.getValidFrom(), LocalDateTime.of(2000, 1, 1, 18, 0));
        assertEquals(persParkingPermission.getValidUntil(), LocalDateTime.of(2020, 1, 1, 0, 0));
    }

    public void shouldCreateParkingPermissionWithoutSpecifiedPeriod() throws Exception {
        //given
        Parking parking = new Parking();
        parkingRepository.save(parking);
        Car car = new Car();
        carRepository.save(car);
        ParkingPermission parkingPermission =
                new ParkingPermission.Builder(parking.getId(), car).build();

        //execute
        parkingPermissionService.addPermission(parkingPermission);

        //validate
        ParkingPermission persParkingPermission = parkingPermissionService.getPermissionForCar(car);
        assertEquals(persParkingPermission.getCar().getCarId(), car.getCarId());
        assertEquals(persParkingPermission.getParkingId(), parking.getId());
        assertEquals(persParkingPermission.getValidFrom(), LocalDateTime.MIN);
        assertEquals(persParkingPermission.getValidUntil(), LocalDateTime.MAX);
    }

    public void shouldCreateParkingPermissionWithSpecifiedValidFrom() throws Exception {
        //given
        Parking parking = new Parking();
        parkingRepository.save(parking);
        Car car = new Car();
        carRepository.save(car);
        ParkingPermission parkingPermission =
                new ParkingPermission.Builder(parking.getId(), car)
                        .validFrom(LocalDateTime.of(2000, 1, 1, 18, 0))
                        .build();

        //execute
        parkingPermissionService.addPermission(parkingPermission);

        //validate
        ParkingPermission persParkingPermission = parkingPermissionService.getPermissionForCar(car);
        assertEquals(persParkingPermission.getCar().getCarId(), car.getCarId());
        assertEquals(persParkingPermission.getParkingId(), parking.getId());
        assertEquals(persParkingPermission.getValidFrom(), LocalDateTime.of(2000, 1, 1, 18, 0));
        assertEquals(persParkingPermission.getValidUntil(), LocalDateTime.MAX);
    }

    public void shouldCreateParkingPermissionWithSpecifiedValidUntil() throws Exception {
        //given
        Parking parking = new Parking();
        parkingRepository.save(parking);
        Car car = new Car();
        carRepository.save(car);
        ParkingPermission parkingPermission =
                new ParkingPermission.Builder(parking.getId(), car)
                        .validUntil(LocalDateTime.of(2020, 1, 1, 18, 0))
                        .build();

        //execute
        parkingPermissionService.addPermission(parkingPermission);

        //validate
        ParkingPermission persParkingPermission = parkingPermissionService.getPermissionForCar(car);
        assertEquals(persParkingPermission.getCar().getCarId(), car.getCarId());
        assertEquals(persParkingPermission.getParkingId(), parking.getId());
        assertEquals(persParkingPermission.getValidFrom(), LocalDateTime.MIN);
        assertEquals(persParkingPermission.getValidUntil(), LocalDateTime.of(2020, 1, 1, 18, 0));
    }

    public void shouldReturnAllAllowedCar() throws Exception {
        //given
        Parking parking = new Parking();
        parkingRepository.save(parking);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        List<Car> expectedAllowedCars = Arrays.asList(car1, car2, car3);
        ParkingPermission parkingPermission1 =
                new ParkingPermission.Builder(parking.getId(), car1).build();
        ParkingPermission parkingPermission2 =
                new ParkingPermission.Builder(parking.getId(), car2).build();
        ParkingPermission parkingPermission3 =
                new ParkingPermission.Builder(parking.getId(), car3).build();

        //execute
        parkingPermissionService.addPermission(parkingPermission1);
        parkingPermissionService.addPermission(parkingPermission2);
        parkingPermissionService.addPermission(parkingPermission3);

        //validate
        List<Car> allowedCars = parkingPermissionService.getAllowedCars(parking);
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
        parkingRepository.save(parking);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        ParkingPermission parkingPermission1 =
                new ParkingPermission.Builder(parking.getId(), car1).build();
        ParkingPermission parkingPermission2 =
                new ParkingPermission.Builder(parking.getId(), car2).build();
        ParkingPermission parkingPermission3 =
                new ParkingPermission.Builder(parking.getId(), car3).build();
        parkingPermissionService.addPermission(parkingPermission1);
        parkingPermissionService.addPermission(parkingPermission2);
        parkingPermissionService.addPermission(parkingPermission3);

        //execute
        parkingPermissionService.removePermission(parkingPermission3);

        //validate
        List<Car> allowedCars = parkingPermissionService.getAllowedCars(parking);
        assertTrue(allowedCars.size() == 2);
        assertNotEquals(allowedCars.get(0).getCarId(), car3.getCarId());
        assertNotEquals(allowedCars.get(1).getCarId(), car3.getCarId());

    }


}
