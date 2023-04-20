package com.example.parkinglot.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="tdxToken")
public class TdxToken {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "tdxToken")
    public String tdxToken ;
}
