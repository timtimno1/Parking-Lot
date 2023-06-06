package com.example.parkinglot.views;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.parkinglot.R;
import com.example.parkinglot.dto.ParkingLotInfoDto;
import com.example.parkinglot.views.adapter.PAdapter;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.ArrayList;
import java.util.List;

public class PagerBottomPopup extends BottomPopupView {
    private final String TAG = PagerBottomPopup.class.getSimpleName();
    private ViewPager2 pager;
    private ParkingLotInfoDto parkingLotInfoDto;

    public PagerBottomPopup(@NonNull Context context, ParkingLotInfoDto parkingLotInfoDto) {
        super(context);
        this.parkingLotInfoDto = parkingLotInfoDto;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_view_pager;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        pager = findViewById(R.id.pager);
        FragmentActivity activity = (FragmentActivity) getContext();
        pager.setAdapter(new PAdapter(activity.getSupportFragmentManager(), activity.getLifecycle(), parkingLotInfoDto));
        TextView TextView = findViewById(R.id.title);
        TextView.setText(parkingLotInfoDto.getParkingLotName());
//        ViewGroup.MarginLayoutParams params = (MarginLayoutParams) getPopupContentView().getLayoutParams();
//        params.bottomMargin = 200;
//        getPopupContentView().setLayoutParams(params);
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
