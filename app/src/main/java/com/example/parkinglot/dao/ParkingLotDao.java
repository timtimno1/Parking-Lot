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

    @Query("SELECT parkingLotName FROM parkingLot WHERE parkingLotName LIKE :keyword")
    List<String> searcParkingLotName(String keyword);

    @Query("SELECT city FROM parkingLot WHERE parkingLotName LIKE :keyword")
    List<String> searcParkingLotCity(String keyword);

    @Query("SELECT parkingLotName FROM parkingLot")
    List<String> defaultParkingLotName();

    @Query("SELECT city FROM parkingLot")
    List<String> defaultParkingLotCity();

    @Query("SELECT * FROM parkingLOt WHERE isFavorite = 1")
    List<ParkingLotEntity> getFavoriteParkingLots();

    @Query("UPDATE parkingLot SET isFavorite = :isFavorite WHERE parkingLotName = :parkingLotName")
    void updateFavorite(String parkingLotName, boolean isFavorite);

}
