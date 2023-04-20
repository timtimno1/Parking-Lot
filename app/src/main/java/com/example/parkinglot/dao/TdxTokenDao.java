package com.example.parkinglot.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.parkinglot.entity.ParkingLot;
import com.example.parkinglot.entity.TdxToken;

@Dao
public interface TdxTokenDao {

    @Query("SELECT * FROM tdxToken")
    TdxToken getToken();

    // Only once use
    @Insert
    void insertToken(TdxToken tdxToken);

    @Query("UPDATE tdxToken SET tdxToken = :tToken WHERE id=1")
    void updateToken(String tToken);

    @Query("SELECT COUNT(tdxToken) FROM tdxToken")
    int getCount();
}
