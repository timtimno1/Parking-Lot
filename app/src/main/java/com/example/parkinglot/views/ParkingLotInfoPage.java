package com.example.parkinglot.views;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.parkinglot.R;

public class ParkingLotInfoPage extends BaseFragment {
    public static ParkingLotInfoPage create(String text){
        ParkingLotInfoPage parkingLotInfoPage = new ParkingLotInfoPage();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        parkingLotInfoPage.setArguments(bundle);
        return parkingLotInfoPage;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.parkinglot_info_page;
    }

    @Override
    public void init(View view) {
        String text = getArguments().getString("text", "XPopup");
        view.<TextView>findViewById(R.id.parkingLot_phone_number).setText(text);
    }
}
