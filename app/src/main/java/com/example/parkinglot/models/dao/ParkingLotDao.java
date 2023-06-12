package com.example.parkinglot.models.dao;

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

    @Query("SELECT * FROM parkingLot WHERE parkingLotName LIKE :isGround")
    List<ParkingLotEntity> getFilterIsGround(String isGround);

    @Query("SELECT * FROM parkingLot WHERE parkingLotName not LIKE :isGround")
    List<ParkingLotEntity> getFilterNotGround(String isGround);

    @Query("SELECT * FROM parkingLot WHERE parkingLotName LIKE :isGround AND isFavorite=:isFavorite")
    List<ParkingLotEntity> getFilterFavoriteAndIsGround(boolean isFavorite, String isGround);

    @Query("SELECT * FROM parkingLot WHERE parkingLotName not LIKE :isGround AND isFavorite=:isFavorite")
    List<ParkingLotEntity> getFilterFavoriteAndNotGround(boolean isFavorite, String isGround);

    @Query("SELECT * FROM parkingLot WHERE city=:city")
    List<ParkingLotEntity> getFilterCity(String city);

    @Query("SELECT * FROM parkingLot WHERE isFavorite=:isFavorite")
    List<ParkingLotEntity> getFilterIsFavorite(boolean isFavorite);

    @Query("SELECT * FROM parkingLot WHERE city=:city and isFavorite=:isFavorite")
    List<ParkingLotEntity> getFilterCityAndIsFavorite(String city, boolean isFavorite);

    @Query("SELECT * FROM parkingLot WHERE city=:city and parkingLotName Like :isGround")
    List<ParkingLotEntity> getFilterCityAndIsGround(String city, String isGround);

    @Query("SELECT * FROM parkingLot WHERE city=:city and parkingLotName not Like :isGround")
    List<ParkingLotEntity> getFilterCityAndNotGround(String city, String isGround);

    @Query("SELECT * FROM parkingLot WHERE city=:city and parkingLotName Like :isGround AND isFavorite=:isFavorite")
    List<ParkingLotEntity> getFilterCityAndFavoriteAndIsGround(String city, String isGround, boolean isFavorite);

    @Query("SELECT * FROM parkingLot WHERE city=:city and parkingLotName not Like :isGround AND isFavorite=:isFavorite")
    List<ParkingLotEntity> getFilterCityAndFavoriteAndNotGround(String city, String isGround, boolean isFavorite);

}
