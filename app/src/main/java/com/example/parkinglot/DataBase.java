package com.example.parkinglot;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.parkinglot.dao.ParkingLotDao;
import com.example.parkinglot.dao.TdxTokenDao;
import com.example.parkinglot.entity.ParkingLot;
import com.example.parkinglot.entity.TdxToken;

@Database(entities = {ParkingLot.class, TdxToken.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract ParkingLotDao parkingLotDao();

    public abstract TdxTokenDao tdxTokenDao();
}
