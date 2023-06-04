package com.example.parkinglot.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.parkinglot.entity.ParkingLotEntity;
import com.example.parkinglot.models.MapModel;
import com.example.parkinglot.models.TdxModel;
import com.example.parkinglot.viewmodels.DTO.ParkingAvailabilityDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapViewModel extends ViewModel {

    private final MapModel mapModel = new MapModel();

    private final TdxModel tdxModel = new TdxModel();

    private final MutableLiveData<List<ParkingLotEntity>> parkingLots = new MutableLiveData<>();

    private final MutableLiveData<String> syncMessage = new MutableLiveData<>();

    private final MutableLiveData<ParkingAvailabilityDTO> carParkAvailability = new MutableLiveData<>();

    private final Map<String, ParkingAvailabilityDTO> carParkNameMappingAvailability = new HashMap<>();

    public LiveData<List<ParkingLotEntity>> getData() {
        return parkingLots;
    }

    public LiveData<String> getSyncMessage() {
        return syncMessage;
    }

    public LiveData<ParkingAvailabilityDTO> getCarParkAvailability() {
        return carParkAvailability;
    }

    public void doAction() {
        mapModel.getParkingLotData((parkingLots::postValue));
    }

    public void doSync() {
        tdxModel.syncTDXParkingLotData((success, message) -> syncMessage.postValue(message));
    }

    public void doParkingAvailability(String carParkID, String carParkName, String city) {
        if(carParkNameMappingAvailability.containsKey(carParkName)) {
            carParkAvailability.setValue(carParkNameMappingAvailability.get(carParkName));
            return;
        }
        tdxModel.getParkingAvailability(carParkID ,city, (success, availability) -> {
            if (success) {
                ParkingAvailabilityDTO parkingAvailabilityDTO = new ParkingAvailabilityDTO(carParkID, carParkName, availability);
                carParkAvailability.postValue(parkingAvailabilityDTO);
                carParkNameMappingAvailability.put(carParkName, parkingAvailabilityDTO);
            }
            else
                carParkAvailability.postValue(new ParkingAvailabilityDTO(carParkID, carParkName, "Not available"));
        });
    }

    public String doParkingAvailability(String carParkName) {
        if( carParkNameMappingAvailability.get(carParkName) != null)
            return carParkNameMappingAvailability.get(carParkName).getAvailability();
        return "Not available";
    }
}
