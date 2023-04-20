package com.example.parkinglot.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.parkinglot.entity.ParkingLot;

import java.util.List;

@Dao
public interface ParkingLotDao {
    @Query("SELECT * FROM parkingLot")
    List<ParkingLot> getAll();

    @Insert
    void insertAll(ParkingLot parkingLot);

    @Query("DELETE FROM parkingLot")
    void delete();
}
