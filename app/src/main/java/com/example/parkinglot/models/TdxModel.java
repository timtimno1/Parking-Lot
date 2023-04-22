package com.example.parkinglot.models;

import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.dao.ParkingLotDao;
import com.example.parkinglot.dao.TdxTokenDao;
import com.example.parkinglot.entity.ParkingLot;
import com.example.parkinglot.entity.TdxToken;
import com.example.parkinglot.utils.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class TdxModel {

    public String getCityPhonePrefix(String city) {
        if(city == "Taipei" || city == "Keelung") return "02";
        else if(city == "Taoyuan" || city == "Hsinchu" || city == "HualienCounty" || city=="YilanCounty") return "03";
        else if(city == "MiaoliCounty") return "037";
        else if(city == "Taichung") return "04";
        else if(city == "NantouCounty") return "049";
        else if(city == "Chiayi" || city == "ChiayiCounty") return "05";
        else if(city == "Tainan") return "06";
        else if(city == "Kaohsiung") return "07";
        else if(city == "PingtungCounty") return "08";
        else if(city == "TaitungCounty") return "089";
        else if(city == "KinmenCounty") return "082";
        else if(city == "LianjiangCounty") return "0836";
        throw new IllegalArgumentException();
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
                    if(tdxTokenDao.getCount() == 0)
                        tdxTokenDao.insertToken(new TdxToken().setTdxToken(new JSONObject(response).getString("access_token")));
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
            if(tdxTokenDao.getCount() == 0) {
                updateTdxToken();
            }
        }).start();
    }

    public void syncTDXParkingLotData(String City) {
        String token = ParkingLotDataBase.getInstance().tdxTokenDao().getToken().tdxToken;
        ParkingLotDao parkingLotDao = ParkingLotDataBase.getInstance().parkingLotDao();
        ParkingLot parkingLot = new ParkingLot();
        // TODO handling the try catch
        HttpRequest httpRequest;
        try {
            httpRequest = new HttpRequest(new URL("https://tdx.transportdata.tw/api/basic/v1/Parking/OffStreet/CarPark/City/"+ City + "?%24select=CarParkName%2C%20CarParkPosition%2C%20Address%2C%20FareDescription%2C%20CarParkID%2C%20CarParkName%2C%20EmergencyPhone&%24top=1&%24count=false&%24format=JSON"));
            httpRequest.setRequestMethod("GET");
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

        Map<String, String> headerData = new HashMap<>();
        headerData.put("accept", "application/json");
        headerData.put("Authorization", "Bearer "+ token);

        httpRequest.request((int httpCode, String response) -> {
            new Thread(() -> {
                try {
                    JSONObject data = new JSONObject(response);
                    parkingLot.carParkID = data.getJSONArray("CarParks").getJSONObject(0).getString("CarParkID");
                    parkingLot.parkingLotName = data.getJSONArray("CarParks").getJSONObject(0).getJSONObject("CarParkName").getString("Zh_tw");
                    parkingLot.address = data.getJSONArray("CarParks").getJSONObject(0).getString("Address");
                    parkingLot.fareDescription = data.getJSONArray("CarParks").getJSONObject(0).getString("FareDescription");
                    parkingLot.longitude = data.getJSONArray("CarParks").getJSONObject(0).getJSONObject("CarParkPosition").getDouble("PositionLon");
                    parkingLot.latitude = data.getJSONArray("CarParks").getJSONObject(0).getJSONObject("CarParkPosition").getDouble("PositionLat");
                    parkingLot.phoneNumber = "" + data.getJSONArray("CarParks").getJSONObject(0).getString("EmergencyPhone");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }).start();
        }, (int httpCode, String errorMessage) -> {

        });

    }

    public void getDatabaseData() {

    }
}
