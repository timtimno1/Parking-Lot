package com.example.parkinglot.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.example.parkinglot.dto.ParkingLotInfoDto;

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

    @ColumnInfo(name = "isFavorite")
    public boolean isFavorite;

    @Ignore
    public static ParkingLotInfoDto toParkingLotInfoDto(ParkingLotEntity parkingLotEntity) {
        ParkingLotInfoDto parkingLotInfoDto = new ParkingLotInfoDto(parkingLotEntity.parkingLotName, parkingLotEntity.city);
        parkingLotInfoDto.setAddress(parkingLotEntity.address);
        parkingLotInfoDto.setPhoneNumber(parkingLotEntity.phoneNumber);
        parkingLotInfoDto.setPrice(parkingLotEntity.fareDescription);
        parkingLotInfoDto.setOpeningHours("24 hours");
        parkingLotInfoDto.setIsFavorite(parkingLotEntity.isFavorite);
        parkingLotInfoDto.setParkingLotId(parkingLotEntity.carParkID);
        return parkingLotInfoDto;
    }
}
