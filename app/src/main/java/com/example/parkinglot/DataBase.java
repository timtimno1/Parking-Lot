package com.example.parkinglot;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.parkinglot.dao.ParkingLotDao;
import com.example.parkinglot.entity.ParkingLot;

@Database(entities = {ParkingLot.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract ParkingLotDao parkingLotDao();
}
