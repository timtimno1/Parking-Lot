package com.example.parkinglot.models;

import android.os.Handler;
import android.os.Looper;
import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.dao.ParkingLotDao;
import com.example.parkinglot.dao.TdxTokenDao;
import com.example.parkinglot.entity.TdxToken;
import com.example.parkinglot.utils.HttpRequest;
import com.example.parkinglot.utils.LatLong;
import com.example.parkinglot.viewmodels.callback.OnParkingLotDataCallBack;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MapModel {

    // Simulate getting park-lot data from backend
    public void retrieveParkingLotData(final OnParkingLotDataCallBack onParkingLotDataCallBack) {

    }

}
