package com.example.parkinglot.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.R;
import com.example.parkinglot.dto.ParkingLotInfoDto;
import com.example.parkinglot.viewmodels.PageBottomPopupViewModel;
import com.example.parkinglot.views.adapter.PAdapter;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.ArrayList;
import java.util.List;

public class PagerBottomPopup extends BottomPopupView {
    private final String TAG = PagerBottomPopup.class.getSimpleName();
    private ViewPager2 pager;
    private ParkingLotInfoDto parkingLotInfoDto;

    private ParkingLotRowAdapter parkingLotRowAdapter;
    private PageBottomPopupViewModel pageBottomPopupViewModel = new PageBottomPopupViewModel();


    public PagerBottomPopup(@NonNull Context context, ParkingLotInfoDto parkingLotInfoDto, ParkingLotRowAdapter parkingLotRowAdapter) {
        super(context);
        this.parkingLotInfoDto = parkingLotInfoDto;
        this.parkingLotRowAdapter = parkingLotRowAdapter;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_view_pager;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate() {
        super.onCreate();
        pager = findViewById(R.id.pager);
        FragmentActivity activity = (FragmentActivity) getContext();
        pager.setAdapter(new PAdapter(activity.getSupportFragmentManager(), activity.getLifecycle(), parkingLotInfoDto));
        TextView TextView = findViewById(R.id.title);
        TextView.setText(parkingLotInfoDto.getParkingLotName());

        ImageButton ib = (ImageButton) findViewById(R.id.favorite_button);
        if(parkingLotInfoDto.getIsFavorite()) {
            ib.setSelected(true);
        }
        else {
            ib.setSelected(false);
        }
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    ParkingLotDataBase.getInstance().parkingLotDao().updateFavorite(parkingLotInfoDto.getParkingLotName(), !parkingLotInfoDto.getIsFavorite());
                    parkingLotInfoDto.setIsFavorite(!parkingLotInfoDto.getIsFavorite());
                    if(parkingLotInfoDto.getIsFavorite()) {
                        ib.setSelected(true);
                        Looper.prepare();
                        Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    else {
                        ib.setSelected(false);
                        Looper.prepare();
                        Toast.makeText(getContext(), "取消收藏", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    getActivity().runOnUiThread(() -> parkingLotRowAdapter.setChange());
                }).start();
            }
        });

        ib.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        //overlay is black with transparency of 0x77 (119)
                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        //clear the overlay
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }

                return false;
            }
        });

        Button subscribeButton = findViewById(R.id.subscription_button);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageBottomPopupViewModel.doSubscribe(parkingLotInfoDto.getCity(), parkingLotInfoDto.getParkingLotId());
                pageBottomPopupViewModel.getSubscribeMessage().observe(PagerBottomPopup.this, message -> {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    protected List<String> getInternalFragmentNames() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Test");
        return list;
    }

    @Override
    protected void onShow() {
        super.onShow();
        Log.d(TAG, "onShow");
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.d(TAG, "onDismiss");
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getScreenHeight(getContext())*.85f);
    }
}
