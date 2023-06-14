package com.example.parkinglot.models;

import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.utils.HttpRequest;
import com.example.parkinglot.viewmodels.callback.OnSubscribeCallBack;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SubscribeModel {
    private final String TAG = TdxModel.class.getSimpleName();

    public void subscribe(String parkingLotId, String city, OnSubscribeCallBack onSubscribeCallBack) {
        new Thread(() -> {
            String deviceToken = ParkingLotDataBase.getInstance().fmcDao().getFMC().deviceToken;
            HttpRequest httpRequest;
            try {
                httpRequest = new HttpRequest(new URL("https://parking.timtimno1.tw//subscribe?city=" + city + "&parkingLotId=" + parkingLotId + "&deviceToken=" + deviceToken));
                httpRequest.setRequestMethod("GET");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Map<String, String> headerData = new HashMap<>();
            headerData.put("X-API-KEY", "Baeldung");
            httpRequest.setHeader(headerData);

            httpRequest.request((int httpCode, String response) -> new Thread(() -> {
                onSubscribeCallBack.onSubscribeReady(true, response);
                System.out.println(response);
            }).start(), (int httpCode, String errorMessage) -> {
                onSubscribeCallBack.onSubscribeReady(false, errorMessage);
            });
        }).start();

    }
}
