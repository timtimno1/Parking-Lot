package com.example.parkinglot.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.parkinglot.entity.ParkingLotEntity;

import java.util.List;

@Dao
public interface ParkingLotDao {
    @Query("SELECT * FROM parkingLot")
    List<ParkingLotEntity> getAll();

    @Insert
    void insertAll(ParkingLotEntity parkingLotEntity);

    @Query("DELETE FROM parkingLot")
    void deleteAll();

    @Delete
    void delete(ParkingLotEntity parkingLotEntity);

    @Query("SELECT * FROM parkingLot where parkingLotName = :parkingLotName")
    ParkingLotEntity selectFromName(String parkingLotName);
}
