package com.example.parkinglot.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="parkingLot")
public class ParkingLotEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "numberOfParkingSpace")
    public int numberOfParkingSpace;

    @ColumnInfo(name = "phoneNumber")
    public String phoneNumber;

    @ColumnInfo(name = "remainingParkingSpace")
    public int remainingParkingSpace;

    @ColumnInfo(name = "parkingLotName")
    public String parkingLotName;

    public String getCarParkID() {
        return carParkID;
    }

    @ColumnInfo(name = "carParkID")
    public String carParkID;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "fareDescription")
    public String fareDescription;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;
}
