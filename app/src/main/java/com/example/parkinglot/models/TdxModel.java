package com.example.parkinglot.models;

import android.util.Log;
import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.dao.ParkingLotDao;
import com.example.parkinglot.dao.TdxTokenDao;
import com.example.parkinglot.entity.ParkingLotEntity;
import com.example.parkinglot.entity.TdxTokenEntity;
import com.example.parkinglot.service.TdxService;
import com.example.parkinglot.service.exception.HttpErrorException;
import com.example.parkinglot.service.exception.TdxTokenExpiredException;
import com.example.parkinglot.utils.Cities;
import com.example.parkinglot.utils.HttpRequest;
import com.example.parkinglot.viewmodels.callback.OnParkingAvailabilityCallBack;
import com.example.parkinglot.viewmodels.callback.OnParkingLotSyncCallBack;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TdxModel {

    private final String TAG = TdxModel.class.getSimpleName();

    private volatile boolean tokenUpdated;

    public void getParkingAvailability(String carParkID, OnParkingAvailabilityCallBack onParkingAvailabilityCallBack) {
        // TODO handling the try catch
        HttpRequest httpRequest;
        try {
            httpRequest = new HttpRequest(new URL("https://tdx.transportdata.tw/api/basic/v1/Parking/OffStreet/ParkingAvailability/City/Taoyuan?$filter=CarParkID eq '" + carParkID + "'"));
            httpRequest.setRequestMethod("GET");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            String token = ParkingLotDataBase.getInstance().tdxTokenDao().getToken().tdxToken;
            Map<String, String> headerData = new HashMap<>();
            headerData.put("accept", "application/json");
            headerData.put("Authorization", "Bearer " + token);

            httpRequest.setHeader(headerData);

            httpRequest.request((int httpCode, String response) -> new Thread(() -> {
                JSONObject data;
                try {
                    data = new JSONObject(response);
                    onParkingAvailabilityCallBack.onParkingAvailabilityReady(true, data.getJSONArray("ParkingAvailabilities").getJSONObject(0).getString("AvailableSpaces"));
                } catch (JSONException e) {

                }

            }).start(), (int httpCode, String errorMessage) -> {

            });
        }).start();
    }

    public void updateTdxToken() {
        // TODO handling the try catch
        HttpRequest httpRequest;
        try {
            httpRequest = new HttpRequest(new URL("https://tdx.transportdata.tw/auth/realms/TDXConnect/protocol/openid-connect/token"));
            httpRequest.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("grant_type", "client_credentials");
        bodyData.put("client_id", "t111598030-cced3820-5137-4071");
        bodyData.put("client_secret", "03716dc7-7bba-44b4-9650-4ff13d84c16c");

        httpRequest.setBodyData(bodyData);
        httpRequest.request((int httpCode, String response) -> new Thread(() -> {
            ParkingLotDataBase db = ParkingLotDataBase.getInstance();
            TdxTokenDao tdxTokenDao = db.tdxTokenDao();
            try {
                if (tdxTokenDao.getCount() == 0)
                    tdxTokenDao.insertToken(new TdxTokenEntity().setTdxToken(new JSONObject(response).getString("access_token")));
                else
                    tdxTokenDao.updateToken(new JSONObject(response).getString("access_token"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            tokenUpdated = true;
        }).start(), (int httpCode, String errorMessage) -> {

        });
    }

    public void checkFirstStartupApp() {
        new Thread(() -> {
            ParkingLotDataBase db = ParkingLotDataBase.getInstance();
            TdxTokenDao tdxTokenDao = db.tdxTokenDao();
            if (tdxTokenDao.getCount() == 0) {
                updateTdxToken();
            }
        }).start();
    }

    /**
     * Synchronizes parking lot data with TDX service in a separate thread
     *
     * @param callBack Callback for handling sync events
     */
    public void syncTDXParkingLotData(OnParkingLotSyncCallBack callBack) {
        new Thread(() -> {
            StringBuilder result = new StringBuilder();
            int[] completedCount = {0};

            Log.d(TAG, "Start sent API to TDX");
            Log.d(TAG, "Waiting response for TDX API");

            List<ParkingLotEntity> parkingLotEntities = callTdxServiceForAllCities(callBack, result, completedCount);

            if (completedCount[0] == Cities.values().length) {
                result.append("sync success");
                Log.d(TAG, "Receive all parking lot from TDX response");
                saveParkingLotDataAndRemovePast(parkingLotEntities);
            }

            callBack.onSyncMessageReady(completedCount[0] == Cities.values().length, result.toString());
        }).start();
    }

    private List<ParkingLotEntity> callTdxServiceForAllCities(OnParkingLotSyncCallBack callBack, StringBuilder result, int[] completedCount) {
        List<ParkingLotEntity> parkingLotEntities = new ArrayList<>();
        TdxService tdxService = new TdxService();
        for (Cities city : Cities.values()) {
            try {
                parkingLotEntities.addAll(tdxService.parkingInfo(city.toString()));
            } catch (IOException e) {
                Log.e(TAG, city + " occur IOException");
                break;
            } catch (TdxTokenExpiredException e) {
                updateTdxToken();
                while (!tokenUpdated) ;
                syncTDXParkingLotData(callBack);
                result.append("Update TDX token");
                break;
            } catch (HttpErrorException e) {
                result.append(city).append(" occur httpError :").append(e.getMessage());
                break;
            }
            completedCount[0]++;
        }
        return parkingLotEntities;
    }

    private void saveParkingLotDataAndRemovePast(List<ParkingLotEntity> parkingLotEntities) {
        ParkingLotDao parkingLotDao = ParkingLotDataBase.getInstance().parkingLotDao();
        parkingLotDao.deleteAll();
        for (ParkingLotEntity parkingLotEntity : parkingLotEntities)
            parkingLotDao.insertAll(parkingLotEntity);
    }
}
