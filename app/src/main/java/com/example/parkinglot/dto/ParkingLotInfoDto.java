package com.example.parkinglot.dto;

import java.io.Serializable;

public class ParkingLotInfoDto implements Serializable {
    private String parkingLotName;
    private String city;
    private String address;
    private String phoneNumber;
    private String price;
    private String openingHours;
    private boolean isFavorite;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public ParkingLotInfoDto(String parkingLotName, String city) {
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

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean getIsFavorite() {return isFavorite;}
}
