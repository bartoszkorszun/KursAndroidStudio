package com.example.carcheck.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
    CAR DATA MODEL
 */

public class AutoData implements Serializable {

    private String make, model, color;

    private List<TankUpRecord> tankUpRecord;

    public AutoData(String make, String model, String color) {
        this.make = make;
        this.model = model;
        this.color = color;

        tankUpRecord = new ArrayList<>();
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<TankUpRecord> getTankUpRecord() {
        return tankUpRecord;
    }

    public void setTankUpRecord(List<TankUpRecord> tankUpRecord) {
        this.tankUpRecord = tankUpRecord;
    }

    @NonNull
    @Override
    public String toString() {
        return make + " " + model + " " + color;
    }
}
