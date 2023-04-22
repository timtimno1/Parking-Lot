package com.example.parkinglot;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.parkinglot.dao.ParkingLotDao;
import com.example.parkinglot.dao.TdxTokenDao;
import com.example.parkinglot.entity.ParkingLot;
import com.example.parkinglot.entity.TdxToken;
import com.example.parkinglot.views.MainActivity;

@Database(entities = {ParkingLot.class, TdxToken.class}, version = 2)
public abstract class ParkingLotDataBase extends RoomDatabase {
    private static ParkingLotDataBase instance = null;

    public abstract ParkingLotDao parkingLotDao();

    public abstract TdxTokenDao tdxTokenDao();

    public static ParkingLotDataBase getInstance() {
        if (MainActivity.getApplicationContextInstance() == null)
            throw new NullPointerException("ApplicationContext is null");
        if(instance == null)
            instance = Room.databaseBuilder(MainActivity.getApplicationContextInstance(), ParkingLotDataBase.class, "ParkingLot").build();
      return instance;
    };
}
