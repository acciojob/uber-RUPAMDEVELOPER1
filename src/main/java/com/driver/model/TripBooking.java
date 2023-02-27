package com.driver.model;

import javax.persistence.*;
import java.util.Scanner;

@Entity
@Table(name ="Tripbooking")
public class TripBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tripbookingId;

    private String fromLocation;

    private String toLocation;

    private int distnaceInKm;

    private int bill;

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    @Enumerated(value = EnumType.STRING)
    private TripStatus tripStatus;

    @ManyToOne
    @JoinColumn
    private Driver driver;

    public TripBooking() {
    }

    public int getTripbookingId() {
        return tripbookingId;
    }

    public void setTripbookingId(int tripbookingId) {
        this.tripbookingId = tripbookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistnaceInKm() {
        return distnaceInKm;
    }

    public void setDistnaceInKm(int distnaceInKm) {
        this.distnaceInKm = distnaceInKm;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn
    private Customer customer;

}