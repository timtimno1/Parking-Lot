package com.example.parkinglot.models;

import android.util.Log;
import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.dao.ParkingLotDao;
import com.example.parkinglot.dao.TdxTokenDao;
import com.example.parkinglot.entity.ParkingLotEntity;
import com.example.parkinglot.entity.TdxTokenEntity;
import com.example.parkinglot.utils.HttpRequest;
import com.example.parkinglot.viewmodels.callback.OnParkingLotSyncCallBack;
import org.json.JSONArray;
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
    private String TAG = TdxModel.class.getSimpleName();


    public enum Citys {
        TAIPEI("Taipei"), KEELUNG("Keelung"), TAOYUAN("Taoyuan"), HSINCHU("Hsinchu"), HUALIENCOUNTY("HualienCounty"), YILANCOUNTY("YilanCounty"), MIAOLICOUNTY("MiaoliCounty"), TAICHUNG("Taichung"), NANTOUCOUNTY("NantouCounty"), CHIAYI("Chiayi"), CHIAYICOUNTY("ChiayiCounty"), TAINAN("Tainan"), KAOHSIUNG("KaohsIung"), PINGTUNGCOUNTY("PingtungCounty"), TAITUNGCOUNTY("TaitungCounty"), KinmenCounty("KinmenCounty"), LienchiangCounty("LienchiangCounty");

        private final String cityString;

        Citys(String cityString) {
            this.cityString = cityString;
        }

        @Override
        public String toString() {
            return cityString;
        }
    }

    public String getCityPhonePrefix(String city) {
        switch (city) {
            case "Taipei":
            case "Keelung":
                return "02";
            case "Taoyuan":
            case "Hsinchu":
            case "HualienCounty":
            case "YilanCounty":
                return "03";
            case "MiaoliCounty":
                return "037";
            case "Taichung":
                return "04";
            case "NantouCounty":
                return "049";
            case "Chiayi":
            case "ChiayiCounty":
                return "05";
            case "Tainan":
                return "06";
            case "Kaohsiung":
                return "07";
            case "PingtungCounty":
                return "08";
            case "TaitungCounty":
                return "089";
            case "KinmenCounty":
                return "082";
            case "LianjiangCounty":
                return "0836";
            default:
                throw new IllegalArgumentException();
        }
    }

    public void updateTdxToken() {
        // TODO handling the try catch
        HttpRequest httpRequest;
        try {
            httpRequest = new HttpRequest(new URL("https://tdx.transportdata.tw/auth/realms/TDXConnect/protocol/openid-connect/token"));
            httpRequest.setRequestMethod("POST");
        }
        catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("grant_type", "client_credentials");
        bodyData.put("client_id", "t111598030-cced3820-5137-4071");
        bodyData.put("client_secret", "03716dc7-7bba-44b4-9650-4ff13d84c16c");

        httpRequest.setBodyData(bodyData);
        httpRequest.request((int httpCode, String response) -> {
            new Thread(() -> {
                ParkingLotDataBase db = ParkingLotDataBase.getInstance();
                TdxTokenDao tdxTokenDao = db.tdxTokenDao();
                try {
                    if (tdxTokenDao.getCount() == 0)
                        tdxTokenDao.insertToken(new TdxTokenEntity().setTdxToken(new JSONObject(response).getString("access_token")));
                    else
                        tdxTokenDao.updateToken(new JSONObject(response).getString("access_token"));
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }, (int httpCode, String errorMessage) -> {

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

    public void syncTDXParkingLotData(OnParkingLotSyncCallBack callBack) {
        ParkingLotDao parkingLotDao = ParkingLotDataBase.getInstance().parkingLotDao();
        parkingLotDao.delete();
        List<Thread> threads = new ArrayList<>();

        for(TdxModel.Citys city : TdxModel.Citys.values()) {
            threads.add(new Thread(new GetTDXParkingLotDataRunnable(city.toString())));
        }

        for(Thread thread : threads) {
            thread.start();
        }

        try {
            for(Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException interruptedException) {
            callBack.onSyncMessageReady(false,"interruptedException");
        }


    }
}

class GetTDXParkingLotDataRunnable implements Runnable {

    private final String TAG = GetTDXParkingLotDataRunnable.class.getSimpleName();

    private final String city;

    private int httpCode;

    private boolean success;

    private String message;

    public GetTDXParkingLotDataRunnable(String city) {
        this.city = city;
        this.httpCode = -1;
    }

    @Override
    public void run() {
        String token = ParkingLotDataBase.getInstance().tdxTokenDao().getToken().tdxToken;
        ParkingLotDao parkingLotDao = ParkingLotDataBase.getInstance().parkingLotDao();
        ParkingLotEntity parkingLotEntity = new ParkingLotEntity();
        // TODO handling the try catch
        HttpRequest httpRequest;
        try {
            httpRequest = new HttpRequest(new URL("https://tdx.transportdata.tw/api/basic/v1/Parking/OffStreet/CarPark/City/" + city + "?$select=CarParkName, CarParkPosition, Address, FareDescription, CarParkID, CarParkName, EmergencyPhone&$count=true&$format=JSON"));
            httpRequest.setRequestMethod("GET");
        }
        catch (ProtocolException e) {
            Log.e(TAG, e.toString());
            setCallBackInfo(-1, false, "Sync Parking Lot error throw the ProtocolException");
            return;
        }
        catch (MalformedURLException e) {
            Log.e(TAG, e.toString());
            setCallBackInfo(-1, false, "Sync Parking Lot error throw the MalformedURLException");
            return;
        }
        catch (IOException e) {
            Log.e(TAG, e.toString());
            setCallBackInfo(-1, false, "Sync Parking Lot error throw the IOException");
            return;
        }

        Map<String, String> headerData = new HashMap<>();
        headerData.put("accept", "application/json");
        headerData.put("Authorization", "Bearer " + token);

        httpRequest.setHeader(headerData);
        httpRequest.request((int httpCode, String response) -> {
            try {
                JSONObject data = new JSONObject(response);
                JSONArray carParks = data.getJSONArray("CarParks");
                for (int i = 0; i < carParks.length(); i++) {
                    // TODO 檢查資料正確性
                    JSONObject carPark = carParks.getJSONObject(i);
                    parkingLotEntity.carParkID = carPark.getString("CarParkID");
                    parkingLotEntity.parkingLotName = carPark.getJSONObject("CarParkName").getString("Zh_tw");
                    parkingLotEntity.address = carPark.getString("Address");
                    parkingLotEntity.fareDescription = carPark.getString("FareDescription");
                    parkingLotEntity.longitude = carPark.getJSONObject("CarParkPosition").getDouble("PositionLon");
                    parkingLotEntity.latitude = carPark.getJSONObject("CarParkPosition").getDouble("PositionLat");
                    parkingLotEntity.phoneNumber = /*getCityPhonePrefix(City) + */ (carPark.has("EmergencyPhone") ? carPark.getString("EmergencyPhone") : "");
                    parkingLotDao.insertAll(parkingLotEntity); // TODO 每次都Insert嗎? 如果已經存在呢?
                }
                Log.d("TdxModel", "Http code: " + httpCode + ", Sync success");
                setCallBackInfo(httpCode, true, "Sync success");
            }
            catch (JSONException e) {
                Log.e(TAG, "Sync Parking Lot error throw the JSONException, Error: " + e);
                setCallBackInfo(-1, false, "JSON: " + e);
            }
        }, (int httpCode, String errorMessage) -> {
            if (httpCode == 401 && errorMessage.equals("invalid token")) {
                setCallBackInfo(httpCode, false, "invalid token");
            }
            else {
                setCallBackInfo(httpCode, false, "Http code: " + httpCode + ", Error message: " + errorMessage);
            }
        });
    }

    public int getHttpCode() {
        return httpCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    private void setCallBackInfo(int httpCode, boolean success, String message) {
        this.httpCode = httpCode;
        this.message = message;
        this.success = success;
    }
}
