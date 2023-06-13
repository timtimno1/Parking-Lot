package com.example.parkinglot.service;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.entity.FMCEntity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FMCService extends FirebaseMessagingService {
    public FMCService() {
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        ParkingLotDataBase.getInstance().fmcDao().insertFMC(new FMCEntity().setDeviceToken(token));
        Log.d("MyService", "onNewToken: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull  RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("MyService", "onMessageReceived: " + remoteMessage.getNotification().getBody());
    }
}