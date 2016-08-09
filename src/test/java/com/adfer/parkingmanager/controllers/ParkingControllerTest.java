package com.adfer.parkingmanager.controllers;

import com.adfer.parkingmanager.Application;
import com.adfer.parkingmanager.domain.Parking;
import com.adfer.parkingmanager.repository.ParkingRepository;
import com.adfer.parkingmanager.services.ParkingService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Adrian_Ferenc on 8/8/2016.
 */
@SpringBootTest(classes = Application.class)
@Test
public class ParkingControllerTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private ParkingService parkingService;

  @Autowired
  private ParkingRepository parkingRepository;

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private ObjectMapper mapper;

  @BeforeClass
  public void setUp() {
    mapper = new ObjectMapper();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @AfterMethod
  public void cleanUp() {
    parkingRepository.deleteAll();
  }


  public void shouldCreateOneParking() throws Exception {
    //given
    Parking parking = new Parking("Parking 1");

    //execute
    mockMvc.perform(post("/parkings/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsBytes(parking)))
        .andExpect(status().isOk());
  }

  public void shouldReturnOneParking() throws Exception {
    //given
    Parking parking = new Parking("Parking 1");
    parkingRepository.save(parking);

    //execute
    mockMvc.perform(get("/parkings/" + parking.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(parking.getId().intValue())))
        .andExpect(jsonPath("$.parkingName", is("Parking 1")));
  }

}
