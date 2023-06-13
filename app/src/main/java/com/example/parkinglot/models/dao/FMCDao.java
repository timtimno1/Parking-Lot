package com.example.parkinglot.models.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.parkinglot.entity.FMCEntity;

import java.util.List;

@Dao
public interface FMCDao {

    @Query("SELECT * FROM FMC")
    List<FMCEntity> getFMC();

    @Insert
    void insertFMC(FMCEntity fmcEntity);
}
