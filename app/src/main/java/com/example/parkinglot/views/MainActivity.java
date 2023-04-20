package com.example.parkinglot.views;

import android.Manifest;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.parkinglot.DataBase;
import com.example.parkinglot.R;
import com.example.parkinglot.dao.ParkingLotDao;
import com.example.parkinglot.entity.ParkingLot;
import com.example.parkinglot.utils.Preconditions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Objects;

public class  MainActivity extends AppCompatActivity {

    private Thread thread;

    private TabLayout tabLayout;

    private ViewPager2 viewPager;

    private static final int REQUEST_LOCATION = 1;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = findViewById(R.id.context);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getLifecycle()));

        viewPager.setUserInputEnabled(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataBase db = Room.databaseBuilder(getApplicationContext(), DataBase.class, "ParkingLot").build();
                ParkingLotDao parkingLotDao = db.parkingLotDao();
                ParkingLot parkingLot = new ParkingLot();
                parkingLot.numberOfParkingSpace = 4;
                parkingLot.typeOfParkingLot="ground";
                parkingLot.openingHours="900";
                parkingLot.remainingParkingSpace=1;
                parkingLot.paymentMethod="cash";
                parkingLot.phoneNumber="092222222";
                parkingLotDao.insertAll(parkingLot);
            }
        }).start();


        // Give the TabLayout with the ViewPager
        // Set the tab text
        tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.main_page);
                    break;
                case 1:
                    tab.setText(R.string.search_page);
                    break;
                case 2:
                    tab.setText(R.string.favorite_page);
                    break;
                case 3:
                    tab.setText(R.string.contact_page);
                    break;
            }
        }).attach();

        // Default select
        try {
            Objects.requireNonNull(tabLayout.getTabAt(0)).select();
        }
        catch (NullPointerException e) {
            Log.e(TAG, "TabLayout.getTabAt return null");
        }
    }

    // inner class
    private class PagerAdapter extends FragmentStateAdapter {

        private FragmentManager fragmentManager;

        public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
            this.fragmentManager = fragmentManager;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Preconditions.checkArgument(position < 4 && position >= 0, "Position must between 1 to 4");
            // get the current selected tab's position and replace the fragment accordingly
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new MapFragment();
                    break;
                case 1:
                    fragment = new SearchFragment();
                    break;
                case 2:
                    fragment = new FavoriteFragment();
                    break;
                case 3:
                    fragment = new ContactFragment();
                    break;
                default:
                    fragment = null;
                    break;
            }
            return Objects.requireNonNull(fragment);
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
