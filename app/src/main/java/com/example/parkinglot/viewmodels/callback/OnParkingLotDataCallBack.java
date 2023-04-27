package com.example.parkinglot.viewmodels.callback;

import com.example.parkinglot.entity.ParkingLotEntity;
import java.util.List;

public interface OnParkingLotDataCallBack {

    /**
     * This method is called when the data for the parking lots is ready.
     *
     * @param parkingLotEntities a list of ParkingLot objects containing the data for each parking lot
     */
    void onParkingLotDataReady(List<ParkingLotEntity> parkingLotEntities);
}
