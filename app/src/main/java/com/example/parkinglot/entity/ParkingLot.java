package com.example.parkinglot.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ParkingLot {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "numberOfParkingSpace")
    public int numberOfParkingSpace;

    @ColumnInfo(name = "phoneNumber")
    public String phoneNumber;

    @ColumnInfo(name = "openingHours")
    public String openingHours;

    @ColumnInfo(name = "paymentMethod")
    public String paymentMethod;

    @ColumnInfo(name = "remainingParkingSpace")
    public int remainingParkingSpace;

    @ColumnInfo(name = "typeOfParkingLot")
    public String typeOfParkingLot;
}
