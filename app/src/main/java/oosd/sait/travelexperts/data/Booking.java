package oosd.sait.travelexperts.data;

import androidx.annotation.NonNull;

public class Booking {
    private int id;
    private String bookingDate, bookingNo;
    private double travelerCount;
    private int customerId;
    private int packageId;

    public Booking(int id, String bookingDate, String bookingNo, double travelerCount, int customerId, int packageId) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.bookingNo = bookingNo;
        this.travelerCount = travelerCount;
        this.customerId = customerId;
        this.packageId = packageId;
    }

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    @NonNull
    @Override
    public String toString() {
        return bookingNo;
    }
}