package com.example.parkinglot.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.parkinglot.models.SubscribeModel;

public class PageBottomPopupViewModel {
    private final SubscribeModel subscribeModel = new SubscribeModel();
    private final MutableLiveData<String> subscribeMessage = new MutableLiveData<>();
    public LiveData<String> getSubscribeMessage() {
        return subscribeMessage;
    }

    public void doSubscribe(String city, String parkingLotId) {
        subscribeModel.subscribe(parkingLotId, city, (success, message) -> {
            subscribeMessage.postValue(message);
        });
    }
}
