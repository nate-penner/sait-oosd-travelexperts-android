package oosd.sait.travelexperts.data;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * Data class representing a customer booking record
 * @author Nate Penner
 * */
public class Booking implements Serializable {
    private int id;
    private String bookingDate, bookingNo;
    private double travelerCount;
    private BookingDetails[] bookingDetails;

    public Booking(int id, String bookingDate, String bookingNo, double travelerCount, BookingDetails[] bookingDetails) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.bookingNo = bookingNo;
        this.travelerCount = travelerCount;
        this.bookingDetails = bookingDetails;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public double getTravelerCount() {
        return travelerCount;
    }

    public void setTravelerCount(double travelerCount) {
        this.travelerCount = travelerCount;
    }

    public BookingDetails[] getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(BookingDetails[] bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    // Override toString to show the booking number
    @NonNull
    @Override
    public String toString() {
        return bookingNo;
    }
}