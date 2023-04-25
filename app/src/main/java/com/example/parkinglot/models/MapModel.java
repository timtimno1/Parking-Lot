package com.example.parkinglot.models;

import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.dao.ParkingLotDao;
import com.example.parkinglot.viewmodels.callback.OnParkingLotDataCallBack;

public class MapModel {

    // Simulate getting park-lot data from backend
    public void getParkingLotData(final OnParkingLotDataCallBack onParkingLotDataCallBack) {
        new Thread(() ->{
            ParkingLotDataBase db = ParkingLotDataBase.getInstance();
            ParkingLotDao parkingLotDao = db.parkingLotDao();

            onParkingLotDataCallBack.onParkingLotDataReady(parkingLotDao.getAll());
        }).start();
    }

}
