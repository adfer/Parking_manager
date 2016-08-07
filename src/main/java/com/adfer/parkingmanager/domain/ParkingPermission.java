package com.adfer.parkingmanager.domain;

import javax.persistence.*;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@Entity
public class ParkingPermission {

    @Id
    @GeneratedValue
    private Long id;

    private Long parkingId;

    @ManyToOne
    @JoinColumn(name = "carId")
    private Car car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
