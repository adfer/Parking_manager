package com.adfer.parkingmanager.domain;

import javax.persistence.*;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@Entity
public class Car {

    @Id
    @GeneratedValue
    private Long carId;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

}
