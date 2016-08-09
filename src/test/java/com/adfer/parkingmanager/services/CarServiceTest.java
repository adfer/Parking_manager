package com.adfer.parkingmanager.services;

import com.adfer.parkingmanager.Application;
import com.adfer.parkingmanager.domain.Car;
import com.adfer.parkingmanager.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@SpringBootTest(classes = Application.class)
@Test
public class CarServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @AfterMethod
    public void cleanUp() {
        carRepository.deleteAll();
    }

    public void shouldCreateCar() throws Exception {
        //given
        Car car = new Car();

        //execute
        carService.addCar(car);

        //verify
        Car persCar = carService.getCar(car.getCarId());
        assertEquals(persCar.getCarId(), car.getCarId());
    }

    public void shouldReturnOneCar() throws Exception {
        //given
        Car car1 = new Car();
        Car car2 = new Car();
        carService.addCar(car1);
        carService.addCar(car2);

        //execute
        Car persCar1 = carService.getCar(car1.getCarId());

        //verify
        assertEquals(persCar1.getCarId(), car1.getCarId());
        assertNotEquals(persCar1.getCarId(), car2.getCarId());
    }

    public void shouldReturnNull_carNotFound() {
        //given
        Car car1 = new Car();
        Car car2 = new Car();
        carService.addCar(car1);
        carService.addCar(car2);

        //execute
        Car persCar = carService.getCar(-1L);

        //verify
        assertNull(persCar);
    }

    public void shouldUpdateCar() throws Exception {
        //given
        Car car = new Car();
        car.setPlateNumber("PLATE_NUMBER");
        carService.addCar(car);

        String expectedPlateNumber = "CHANGED_PLATE_NUMBER";
        car.setPlateNumber(expectedPlateNumber);

        //execute
        Car changedCar = carService.updateCar(car);

        //verify
        assertEquals(changedCar.getCarId(), car.getCarId());
        assertEquals(changedCar.getPlateNumber(), expectedPlateNumber);
    }

    public void testChangeCar_shouldReturnNull() {
        //given
        Car car = new Car();

        //execute
        Car changedCar = carService.updateCar(car);

        //verify
        assertNull(changedCar);
    }

    public void shouldRemoveCar() throws Exception {
        //given
        Car car1 = new Car();
        Car car2 = new Car();
        carService.addCar(car1);
        carService.addCar(car2);

        //execute
        carService.removeCar(car1);

        //verify
        Car removedCar = carService.getCar(car1.getCarId());
        assertNull(removedCar);
    }

    public void shouldReturnAllCars() throws Exception {
        //given
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        carService.addCar(car1);
        carService.addCar(car2);
        carService.addCar(car3);

        //execute
        List<Car> cars = carService.getAllCars();

        //verify
        assertTrue(cars.size() == 3);
    }

}
