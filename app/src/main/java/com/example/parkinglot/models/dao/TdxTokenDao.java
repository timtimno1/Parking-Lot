package com.example.parkinglot.models.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.parkinglot.entity.TdxTokenEntity;

@Dao
public interface TdxTokenDao {

    @Query("SELECT * FROM TdxToken")
    TdxTokenEntity getToken();

    // Only once use
    @Insert
    void insertToken(TdxTokenEntity tdxTokenEntity);

    @Query("UPDATE TdxToken SET tdxToken = :tToken WHERE id=1")
    void updateToken(String tToken);

    @Query("SELECT COUNT(tdxToken) FROM TdxToken")
    int getCount();
}
