package com.adfer.parkingmanager.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by adrianferenc on 07.08.2016.
 */
@Entity
public class Parking {

    @Id
    @GeneratedValue
    private Long id;

    private String parkingName;

    public Parking() {
    }

    public Parking(String parkingName) {
        this.parkingName = parkingName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }
}
