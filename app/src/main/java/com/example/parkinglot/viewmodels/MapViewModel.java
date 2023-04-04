package com.example.parkinglot.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.parkinglot.models.MapModel;
import com.example.parkinglot.utils.LatLong;

public class MapViewModel extends ViewModel {

    private final MapModel mapModel = new MapModel();

    private final MutableLiveData<LatLong> mData = new MutableLiveData<>();

    public LiveData<LatLong> getData() {
        return mData;
    }

    public void doAction() {
        mapModel.retrieveParkingLotData((mData::setValue));
    }
}
