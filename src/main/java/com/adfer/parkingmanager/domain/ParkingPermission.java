package com.adfer.parkingmanager.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime validFrom;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime validUntil;

    public ParkingPermission(){}

    private ParkingPermission(Builder builder) {
        this.parkingId = builder.parkingId;
        this.car = builder.car;
        this.validFrom = builder.validFrom;
        this.validUntil = builder.validUntil;
    }

    public Long getId() {
        return id;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public Car getCar() {
        return car;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public static class Builder {


        private final Long parkingId;

        private final Car car;

        private LocalDateTime validFrom = LocalDateTime.MIN;

        private LocalDateTime validUntil = LocalDateTime.MAX;

        public Builder(Long parkingId, Car car) {
            this.parkingId = parkingId;
            this.car = car;
        }

        public Builder validFrom(LocalDateTime validFrom) {
            this.validFrom = validFrom;
            return this;
        }

        public Builder validUntil(LocalDateTime validUntil) {
            this.validUntil = validUntil;
            return this;
        }

        public ParkingPermission build() {
            return new ParkingPermission(this);
        }

    }
}
