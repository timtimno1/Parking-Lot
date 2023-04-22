package com.example.parkinglot.models;

import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.dao.TdxTokenDao;
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

public class TdxModel {
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
}
