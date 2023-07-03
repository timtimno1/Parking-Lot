package com.example.parkinglot.views.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.parkinglot.utils.Preconditions;
import com.example.parkinglot.views.ContactFragment;
import com.example.parkinglot.views.FavoriteFragment;
import com.example.parkinglot.views.MapFragment;
import com.example.parkinglot.views.SearchFragment;

import java.util.Objects;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
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
