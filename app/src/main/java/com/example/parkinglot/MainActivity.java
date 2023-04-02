package com.example.parkinglot;

import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = findViewById(R.id.context);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getLifecycle()));

        viewPager.setUserInputEnabled(false);

        // Give the TabLayout with the ViewPager
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

        tabLayout.getTabAt(0).select();
    }

    class PagerAdapter extends FragmentStateAdapter {

        private FragmentManager fragmentManager;

        public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
            this.fragmentManager = fragmentManager;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // get the current selected tab's position and replace the fragment accordingly
            Fragment fragment = null;
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
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
