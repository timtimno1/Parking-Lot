package com.example.parkinglot.views;

import android.Manifest;
import android.content.Context;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.parkinglot.R;
import com.example.parkinglot.viewmodels.MainActivityViewModel;
import com.example.parkinglot.views.adapter.MainPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class  MainActivity extends AppCompatActivity {

    private static Context applicationContextInstance = null;

    private static final int REQUEST_LOCATION = 1;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivityViewModel mainActivityViewModel;
        ViewPager2 viewPager;
        TabLayout tabLayout;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        MainActivity.applicationContextInstance = getApplicationContext();

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = findViewById(R.id.context);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), getLifecycle()));
        viewPager.setUserInputEnabled(false);

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
                default:
                    throw new IllegalStateException("Unexpected value: " + position);
            }
        }).attach();

        // Default select
        try {
            Objects.requireNonNull(tabLayout.getTabAt(0)).select();
        }
        catch (NullPointerException e) {
            Log.e(TAG, "TabLayout.getTabAt return null");
        }

        mainActivityViewModel.doCheck();
    }

    public static Context getApplicationContextInstance() {
        if(MainActivity.applicationContextInstance == null )
            throw new NullPointerException("ApplicationContext is null");
        else
            return MainActivity.applicationContextInstance;
    }

    public static void setApplicationContextInstance(Context applicationContextInstance) {
        MainActivity.applicationContextInstance = applicationContextInstance;
    }
}
