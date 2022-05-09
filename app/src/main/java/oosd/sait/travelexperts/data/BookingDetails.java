package oosd.sait.travelexperts.data;

import java.io.Serializable;

/**
 * A class representing the details of a booking record
 * @author Nate
 * */
public class BookingDetails implements Serializable {
    private String tripStart, tripEnd, description, destination;
    private double basePrice;

    public BookingDetails(String tripStart, String tripEnd, String description, String destination, double basePrice) {
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.description = description;
        this.destination = destination;
        this.basePrice = basePrice;
    }

    // Getters and setters
    public String getTripStart() {
        return tripStart;
    }

    public void setTripStart(String tripStart) {
        this.tripStart = tripStart;
    }

    public String getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(String tripEnd) {
        this.tripEnd = tripEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}
