package com.example.carcheck.model;

import java.io.Serializable;
import java.util.Date;

/*
    DATA MODEL OF SINGLE TANK UP
 */

public class TankUpRecord implements Serializable {

    private Date tankUpDate;
    private Double mileage, liters, costInPLN;

    public TankUpRecord(Date tankUpDate, Double mileage, Double liters, Double costInPLN) {
        this.tankUpDate = tankUpDate;
        this.mileage = mileage;
        this.liters = liters;
        this.costInPLN = costInPLN;
    }

    public Date getTankUpDate() {
        return tankUpDate;
    }

    public void setTankUpDate(Date tankUpDate) {
        this.tankUpDate = tankUpDate;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Double getLiters() {
        return liters;
    }

    public void setLiters(Double liters) {
        this.liters = liters;
    }

    public Double getCostInPLN() {
        return costInPLN;
    }

    public void setCostInPLN(Double costInPLN) {
        this.costInPLN = costInPLN;
    }
}
