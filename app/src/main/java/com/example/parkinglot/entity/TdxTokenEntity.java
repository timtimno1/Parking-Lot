package com.example.parkinglot.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="tdxToken")
public class TdxTokenEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "tdxToken")
    public String tdxToken ;

    public TdxTokenEntity setTdxToken(String tdxToken) {
        this.tdxToken = tdxToken;
        return this;
    }
}
