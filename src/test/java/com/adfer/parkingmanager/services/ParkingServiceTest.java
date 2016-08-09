package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.Application;
import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.repository.CarRepository;
import com.adfer.parkingmanager.repository.ParkingPermissionRepository;
import com.adfer.parkingmanager.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

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
    private ParkingPermissionRepository parkingPermissionRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private CarRepository carRepository;

    @AfterMethod
    public void cleanUp(){
        parkingPermissionRepository.deleteAll();
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

    public void shouldReturnParking() throws Exception {
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

    public void shouldUpdateParking() throws Exception {
        //given
        Parking parking = new Parking();
        parking.setParkingName("PARKING_NAME");
        parkingService.createParking(parking);

        String expectedParkingName = "CHANGED_PARKING_NAME";
        parking.setParkingName(expectedParkingName);

        //execute
        Parking changedParking = parkingService.updateParking(parking);

        //verify
        assertEquals(changedParking.getId(), parking.getId());
        assertEquals(changedParking.getParkingName(), expectedParkingName);
    }

    public void testUpdateParking_shouldReturnNull() throws Exception {
        //given
        Parking parking = new Parking();

        //execute
        Parking changedParking = parkingService.updateParking(parking);

        //verify
        assertNull(changedParking);
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

}
