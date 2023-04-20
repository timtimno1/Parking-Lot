package com.example.parkinglot.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.parkinglot.entity.ParkingLot;

import java.util.List;

@Dao
public interface ParkingLotDao {
    @Query("SELECT * FROM parkingLot")
    List<ParkingLot> getAll();
}
