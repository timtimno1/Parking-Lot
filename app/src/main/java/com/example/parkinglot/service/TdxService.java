package com.example.parkinglot.service;

import android.util.Log;
import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.entity.ParkingLotEntity;
import com.example.parkinglot.service.exception.HttpErrorException;
import com.example.parkinglot.service.exception.TdxTokenExpiredException;
import com.example.parkinglot.utils.HttpRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.parkinglot.utils.CityPhonePrefix.autoAddRegionNumber;

public class TdxService {

    private int httpCode = -1;

    private String errorMessage;

    private boolean isInvalidToken;

    private static final String TAG = TdxService.class.getSimpleName();

    /**
     * Retrieves information about a parking lot in the specified city
     *
     * @param city The name of the city to retrieve parking lot information for
     * @return ParkingLotEntity object containing the information about the parking lot
     * @throws IOException              When an error occurs while retrieving the parking lot information
     * @throws TdxTokenExpiredException When the Tdx token has expired and a new one is required
     * @throws HttpErrorException       When an HTTP error occurs while retrieving the parking lot information
     */
    public List<ParkingLotEntity> parkingInfo(String city) throws IOException, TdxTokenExpiredException, HttpErrorException {
        String token = ParkingLotDataBase.getInstance().tdxTokenDao().getToken().tdxToken;

        List<ParkingLotEntity> parkingLotEntitles = new ArrayList<>();
        HttpRequest httpRequest;
        try {
            httpRequest = new HttpRequest(new URL("https://tdx.transportdata.tw/api/basic/v1/Parking/OffStreet/CarPark/City/" + city + "?$select=CarParkName, CarParkPosition, Address, FareDescription, CarParkID, CarParkName, EmergencyPhone&$count=true&$format=JSON"));
            httpRequest.setRequestMethod("GET");
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            throw e;
        }

        Map<String, String> headerData = new HashMap<>();
        headerData.put("accept", "application/json");
        headerData.put("Authorization", "Bearer " + token);

        httpRequest.setHeader(headerData);
        Thread thread = httpRequest.request((int httpCode, String response) -> {
            this.httpCode = httpCode;
            try {
                JSONObject data = new JSONObject(response);
                convertJsonToParkingLotEntity(data.getJSONArray("CarParks"), parkingLotEntitles, city);
                Log.d(TAG, "Http code: " + httpCode + ", " + city + " sync success");
            } catch (JSONException e) {
                Log.e(TAG, "Http code: " + httpCode + ", " + city + "Sync Parking Lot error throw the JSONException, Error: " + e);
            }
        }, (int httpCode, String errorMessage) -> {
            this.httpCode = httpCode;
            this.errorMessage = errorMessage;
            if (httpCode == 401 && errorMessage.equals("invalid token"))
                this.isInvalidToken = true;
        });

        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (isInvalidToken)
            throw new TdxTokenExpiredException("invalid token");
        else if (httpCode == 200)
            return parkingLotEntitles;
        else
            throw new HttpErrorException(httpCode + ": " + errorMessage);
    }

    private void convertJsonToParkingLotEntity(JSONArray carParks, List<ParkingLotEntity> parkingLotEntitles, String city) throws JSONException {
        for (int i = 0; i < carParks.length(); i++) {
            // TODO 檢查資料正確性
            ParkingLotEntity parkingLotEntity = new ParkingLotEntity();
            JSONObject carPark = carParks.getJSONObject(i);
            parkingLotEntity.carParkID = carPark.getString("CarParkID");
            parkingLotEntity.parkingLotName = carPark.getJSONObject("CarParkName").getString("Zh_tw");
            parkingLotEntity.address = carPark.getString("Address");
            parkingLotEntity.fareDescription = carPark.getString("FareDescription");
            parkingLotEntity.longitude = carPark.getJSONObject("CarParkPosition").getDouble("PositionLon");
            parkingLotEntity.latitude = carPark.getJSONObject("CarParkPosition").getDouble("PositionLat");

            try {
                parkingLotEntity.phoneNumber = autoAddRegionNumber((carPark.has("EmergencyPhone") ? carPark.getString("EmergencyPhone") : ""), city);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, e.toString());
            }
            parkingLotEntitles.add(parkingLotEntity);
        }
    }
}
