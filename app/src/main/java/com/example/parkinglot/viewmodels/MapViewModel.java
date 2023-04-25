package com.example.parkinglot.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.parkinglot.entity.ParkingLot;
import com.example.parkinglot.models.MapModel;
import com.example.parkinglot.models.TdxModel;

import java.util.List;

public class MapViewModel extends ViewModel {

    private final MapModel mapModel = new MapModel();

    private final TdxModel tdxModel = new TdxModel();

    private final MutableLiveData<List<ParkingLot>> parkingLots = new MutableLiveData<>();

    private final MutableLiveData<String> syncMessage = new MutableLiveData<>();

    private final MutableLiveData<Boolean> success = new MutableLiveData<>();

    public LiveData<List<ParkingLot>> getData() {
        return parkingLots;
    }

    public LiveData<String> getSyncMessage() {
        return syncMessage;
    }

    public void doAction() {
        mapModel.getParkingLotData((parkingLots::postValue));
    }

    public void doSync() {
        tdxModel.syncTDXParkingLotData((success, message) -> {
            // TODO 回傳success變數
            syncMessage.postValue(message);
        }, "Taoyuan");
    }
}
