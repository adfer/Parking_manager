package com.adfer.parkingmanager.controllers;

import com.adfer.parkingmanager.Application;
import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.repository.CarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertTrue;

/**
 * Created by Adrian_Ferenc on 8/8/2016.
 */
@SpringBootTest(classes = Application.class)
@Test
public class CarControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeClass
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterMethod
    public void cleanUp() {
        carRepository.deleteAll();
    }

    public void shouldAddOneCar() throws Exception {
        //given
        mapper = new ObjectMapper();
        Car car1 = new Car();

        //execute
        mockMvc.perform(post("/cars/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(car1)))
                .andExpect(status().isOk());

        //verify
        Long carsCount = carRepository.count();
        assertTrue(carsCount == 1);
    }

    public void shouldReturnOneCar() throws Exception {
        //given
        Car car = new Car();
        carRepository.save(car);

        //execute
        mockMvc.perform(get("/cars/" + car.getCarId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId", is(car.getCarId().intValue())));
    }

    public void shouldReturnNotFoundStatus_carNotFound() throws Exception {
        //execute
        mockMvc.perform(get("/cars/" + -1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    public void shouldReturnBadRequest_incorrectCarId() throws Exception {
        //given
        String INCORRECT_PATH_VARIABLE_VALUE = "CAR_ID";

        //execute
        mockMvc.perform(get("/cars/" + INCORRECT_PATH_VARIABLE_VALUE))
                .andExpect(status().isBadRequest());
    }

    public void shouldReturnListOfCars() throws Exception {
        //given
        List<String> expectedPlateNumber = Arrays.asList("PLATE_NUMBER_1", "PLATE_NUMBER_2", "PLATE_NUMBER_3");
        Car car1 = new Car();
        car1.setPlateNumber("PLATE_NUMBER_1");
        Car car2 = new Car();
        car2.setPlateNumber("PLATE_NUMBER_2");
        Car car3 = new Car();
        car3.setPlateNumber("PLATE_NUMBER_3");
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);

        //execute
        mockMvc.perform(get("/cars/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[0].plateNumber", isIn(expectedPlateNumber)))
                .andExpect(jsonPath("$[1].plateNumber", isIn(expectedPlateNumber)))
                .andExpect(jsonPath("$[2].plateNumber", isIn(expectedPlateNumber)));

    }

    public void shouldChangeCarData() throws Exception {
        //given
        mapper = new ObjectMapper();

        Car car = new Car();
        car.setPlateNumber("PLATE_NUMBER");
        carRepository.save(car);

        Car changedCar = new Car();
        changedCar.setCarId(car.getCarId());
        changedCar.setPlateNumber("UPDATED_PLATE_NUMBER");

        //execute
        mockMvc.perform(put("/cars/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(changedCar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId", is(car.getCarId().intValue())))
                .andExpect(jsonPath("$.plateNumber", is("UPDATED_PLATE_NUMBER")));
    }

    public void testChangeCarData_shouldReturnNotFound() throws Exception {
        //given
        mapper = new ObjectMapper();
        Car car = new Car();

        //execute
        mockMvc.perform(put("/cars/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(car)))
                .andExpect(status().isNotFound());
    }

}
