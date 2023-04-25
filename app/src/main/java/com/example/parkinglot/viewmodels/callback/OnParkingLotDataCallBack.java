package com.example.parkinglot.viewmodels.callback;

import com.example.parkinglot.entity.ParkingLot;
import java.util.List;

public interface OnParkingLotDataCallBack {

    /**
     * This method is called when the data for the parking lots is ready.
     *
     * @param parkingLots a list of ParkingLot objects containing the data for each parking lot
     */
    void onParkingLotDataReady(List<ParkingLot> parkingLots);
}
