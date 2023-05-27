package com.example.parkinglot.viewmodels.DTO;

public class ParkingAvailabilityDTO {

    private String carParkID;

    private String carParkName;

    private String availability;

    public ParkingAvailabilityDTO(String carParkID, String carParkName, String availability) {
        this.carParkID = carParkID;
        this.carParkName = carParkName;
        this.availability = availability;
    }

    public String getCarParkID() {
        return carParkID;
    }

    public void setCarParkID(String carParkID) {
        this.carParkID = carParkID;
    }

    public String getCarParkName() {
        return carParkName;
    }

    public void setCarParkName(String carParkName) {
        this.carParkName = carParkName;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
