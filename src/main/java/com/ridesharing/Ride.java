package com.ridesharing;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ride {
    private int id;
    private int driverId;
    private String startPoint;
    private String destination;
    private LocalDate tripDate;    // Separate Date
    private LocalTime tripTime;    // Separate Time
    private int availableSeats;
    private double fare;           // New field for fare

    public Ride(int driverId, String startPoint, String destination, LocalDate tripDate, LocalTime tripTime, int availableSeats, double fare) {
        this.driverId = driverId;
        this.startPoint = startPoint;
        this.destination = destination;
        this.tripDate = tripDate;
        this.tripTime = tripTime;
        this.availableSeats = availableSeats;
        this.fare = fare;
    }

    public Ride(int id, int driverId, String startPoint, String destination, LocalDate tripDate, LocalTime tripTime, int availableSeats, double fare) {
        this.id = id;
        this.driverId = driverId;
        this.startPoint = startPoint;
        this.destination = destination;
        this.tripDate = tripDate;
        this.tripTime = tripTime;
        this.availableSeats = availableSeats;
        this.fare = fare;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public LocalTime getTripTime() {
        return tripTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getFare() {
        return fare;
    }

    @Override
    public String toString() {
        return "Ride [Start: " + startPoint + ", Destination: " + destination + ", Date: " + tripDate + ", Time: " + tripTime + ", Seats: " + availableSeats + ", Fare: $" + fare + "]";
    }
}
