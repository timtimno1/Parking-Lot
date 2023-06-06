package com.example.parkinglot.dto;

public class SearchedParkingLotDto {
    private String parkingLotName;
    private String city;

    public SearchedParkingLotDto(String parkingLotName, String city) {
        this.parkingLotName = parkingLotName;
        this.city = city;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
