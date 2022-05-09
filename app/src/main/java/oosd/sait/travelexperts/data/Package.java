package oosd.sait.travelexperts.data;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * Data class representing a vacation package record
 * @author Nate Penner
 * */
public class Package {
    private int id;
    private String name;
    private String startDate, endDate;
    private String description;
    private double basePrice;
    private double agencyCommission;

    public Package(int id, String name, String startDate, String endDate, String description, double basePrice, double agencyCommission) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.basePrice = basePrice;
        this.agencyCommission = agencyCommission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getAgencyCommission() {
        return agencyCommission;
    }

    public void setAgencyCommission(double agencyCommission) {
        this.agencyCommission = agencyCommission;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
