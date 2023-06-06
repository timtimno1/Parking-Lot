package com.example.parkinglot.views.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.parkinglot.dto.ParkingLotInfoDto;
import com.example.parkinglot.views.ParkingLotInfoPage;

public class PAdapter extends FragmentStateAdapter {
    private ParkingLotInfoDto parkingLotInfoDto;

    public PAdapter(@NonNull FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
    }

    public PAdapter(@NonNull FragmentManager fm, Lifecycle lifecycle, ParkingLotInfoDto parkingLotInfoDto) {
        super(fm, lifecycle);
        this.parkingLotInfoDto = parkingLotInfoDto;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ParkingLotInfoPage.create(parkingLotInfoDto);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
