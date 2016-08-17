package com.adfer.parkingmanager.controllers;

import com.adfer.parkingmanager.Application;
import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.domain.ParkingPermission;
import com.adfer.parkingmanager.repository.ParkingPermissionRepository;
import com.adfer.parkingmanager.services.CarService;
import com.adfer.parkingmanager.services.ParkingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import springfox.documentation.spring.web.ObjectMapperConfigurer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;

/**
 * Created by adrianferenc on 09.08.2016.
 */

@SpringBootTest(classes = Application.class)
@Test
public class ParkingPermissionControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ParkingPermissionRepository parkingPermissionRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper mapper;

    private MockMvc mockMvc;

    @BeforeClass
    private void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterClass
    private void clenUp() {
        parkingPermissionRepository.deleteAll();
    }

    public void shouldAddParkingPermission() throws Exception {
        //given
        mapper = new ObjectMapper();

        Car car = new Car("Plate number");
        Parking parking = new Parking("Parking 1");

        carService.addCar(car);
        parkingService.createParking(parking);

        ParkingPermission parkingPermission =
                new ParkingPermission.Builder(parking.getId(), car)
                        .build();

        //execute
        mockMvc.perform(post("/permissions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(parkingPermission)))
                .andExpect(status().isOk());

        //verify
        assertEquals(parkingPermissionRepository.count(), 1L);
    }

}
