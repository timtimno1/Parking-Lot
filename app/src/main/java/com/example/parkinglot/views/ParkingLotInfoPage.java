package com.example.parkinglot.views;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.parkinglot.R;
import com.example.parkinglot.dto.ParkingLotInfoDto;

public class ParkingLotInfoPage extends BaseFragment {
    public static ParkingLotInfoPage create(ParkingLotInfoDto parkingLotInfoDto) {
        ParkingLotInfoPage parkingLotInfoPage = new ParkingLotInfoPage();
        // Put the parkingLotInfoDto into a bundle and pass it to the fragment.
        Bundle bundle = new Bundle();
        bundle.putSerializable("parkingLotInfoDto", parkingLotInfoDto);
        parkingLotInfoPage.setArguments(bundle);
        return parkingLotInfoPage;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.parkinglot_info_page;
    }

    @Override
    public void init(View view) {
        ParkingLotInfoDto parkingLotInfoDto = (ParkingLotInfoDto) getArguments().getSerializable("parkingLotInfoDto");
        view.<TextView>findViewById(R.id.parkingLot_phone_number).setText(parkingLotInfoDto.getPhoneNumber());
        view.<TextView>findViewById(R.id.parkingLot_address).setText(parkingLotInfoDto.getAddress());
        view.<TextView>findViewById(R.id.parkingLot_price).setText(parkingLotInfoDto.getPrice());
        view.<TextView>findViewById(R.id.parkingLot_opening_hours).setText(parkingLotInfoDto.getOpeningHours());
    }
}
