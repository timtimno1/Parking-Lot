package com.example.parkinglot.views.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.parkinglot.views.TestFragment;

public class PAdapter extends FragmentStatePagerAdapter {
    String[] titles;
    public PAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public PAdapter(@NonNull FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }



    @Override
    public Fragment getItem(int position) {
        return TestFragment.create("XPopup默认是Dialog实现，由于Android的限制，Dialog中默认无法使用Fragment。\n\n所以要想在弹窗中使用Fragment，要设置isViewMode(true).");
    }



    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles!=null ? titles[position] : "xpopup";
    }
}
