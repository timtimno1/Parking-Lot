package com.example.parkinglot.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FMC")
public class FMCEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "deviceToken")
    public String deviceToken ;

    public FMCEntity setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
        return this;
    }
}
