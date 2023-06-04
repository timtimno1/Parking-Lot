package com.example.parkinglot.views.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.parkinglot.views.TestFragment;

public class PAdapter extends FragmentStateAdapter {
    String[] content;

    public PAdapter(@NonNull FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
    }

    public PAdapter(@NonNull FragmentManager fm, Lifecycle lifecycle, String[] content) {
        super(fm, lifecycle);
        this.content = content;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return TestFragment.create(content[position]);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
