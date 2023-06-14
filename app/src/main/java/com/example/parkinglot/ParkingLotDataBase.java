package com.example.parkinglot;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.parkinglot.entity.FMCEntity;
import com.example.parkinglot.models.dao.FMCDao;
import com.example.parkinglot.models.dao.ParkingLotDao;
import com.example.parkinglot.models.dao.TdxTokenDao;
import com.example.parkinglot.entity.ParkingLotEntity;
import com.example.parkinglot.entity.TdxTokenEntity;
import com.example.parkinglot.views.MainActivity;

@Database(entities = {ParkingLotEntity.class, TdxTokenEntity.class, FMCEntity.class}, version = 5)
public abstract class ParkingLotDataBase extends RoomDatabase {
    private static ParkingLotDataBase instance = null;

    public abstract ParkingLotDao parkingLotDao();

    public abstract TdxTokenDao tdxTokenDao();

    public abstract FMCDao fmcDao();

    public static ParkingLotDataBase getInstance() {
        if (MainActivity.getApplicationContextInstance() == null)
            throw new NullPointerException("ApplicationContext is null");
        if(instance == null)
            instance = Room.databaseBuilder(MainActivity.getApplicationContextInstance(), ParkingLotDataBase.class, "ParkingLot").fallbackToDestructiveMigration().build();
      return instance;
    };
}
