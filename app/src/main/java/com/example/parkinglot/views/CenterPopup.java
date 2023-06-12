package com.example.parkinglot.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.R;
import com.example.parkinglot.dto.ParkingLotInfoDto;
import com.example.parkinglot.entity.ParkingLotEntity;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.CenterPopupView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CenterPopup extends CenterPopupView {

    Spinner cityFilter;

    Spinner isGroundFilter;

    Spinner isFavoriteFilter;

    String[] cities={"全選", "臺北", "基隆", "桃園", "新竹", "花蓮", "宜蘭", "苗栗", "臺中", "南投", "嘉義", "嘉義縣", "臺南", "高雄", "屏東", "臺東", "金門", "連江縣"};

    String[] groundOptions={"全選", "是", "不是"};

    String[] favoriteOptions={"全選", "是", "不是"};

    private String currentCity;

    private String currentIsGround;

    private String currentIsFavorite;

    ParkingLotRowAdapter parkingLotRowAdapter;

    List<ParkingLotInfoDto> parkingLotInfoDtos = new ArrayList<>();

    public CenterPopup(@NonNull Context context, ParkingLotRowAdapter p) {
        super(context);
        this.parkingLotRowAdapter = p;
    }
    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.center_popup;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();
        SetOptionsForSpinner();
        setPreferences();
        getCurrentSelectedItem();
        SetListenerForClearFilter();
        SetListenerForApplyChange();
    }

    private void SetOptionsForSpinner() {
        cityFilter = findViewById(R.id.filter_city);
        isGroundFilter = findViewById(R.id.filter_isGround);
        isFavoriteFilter = findViewById(R.id.filter_isFavorite);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, cities);
        ArrayAdapter<String> groundAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, groundOptions);
        ArrayAdapter<String> favoriteAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, favoriteOptions);
        cityFilter.setAdapter(cityAdapter);
        isGroundFilter.setAdapter(groundAdapter);
        isFavoriteFilter.setAdapter(favoriteAdapter);
    }

    public void setPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        String city = sharedPreferences.getString("City", "全選");
        String isGround = sharedPreferences.getString("isGround", "全選");
        String isFavorite = sharedPreferences.getString("isFavorite", "全選");

        int cityIndex = 0;
        int isGroundIndex = 0;
        int isFavoriteIndex = 0;

        for (int i = 0; i < cities.length; i++){
            if(cities[i] == city) {
                cityIndex = i;
            }
        }

        for (int j = 0; j < groundOptions.length; j++){
            if(groundOptions[j] == isGround) {
                isGroundIndex = j;
            }
        }

        for (int k = 0; k < favoriteOptions.length; k++){
            if(favoriteOptions[k] == isFavorite) {
                isFavoriteIndex = k;
            }
        }

        cityFilter.setSelection(cityIndex);
        isGroundFilter.setSelection(isGroundIndex);
        isFavoriteFilter.setSelection(isFavoriteIndex);

    }

    public void getCurrentSelectedItem() {
        cityFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentCity = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        isGroundFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentIsGround = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        isFavoriteFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentIsFavorite = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void SetListenerForApplyChange() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            sharedPreferences.edit().putString("isFavorite", currentIsFavorite).putString("isGround", currentIsGround).putString("City", currentCity).commit();
            Toast.makeText(getHostWindow().getContext(), "城市為" + currentCity + ", " + currentIsGround + "地下停車場, " + currentIsFavorite + "最愛", Toast.LENGTH_SHORT).show();
            changeRecyclerViewItem();
            dismiss();
        });
        findViewById(R.id.tv_cancel).setOnClickListener(view -> dismiss());
    }

    public void SetListenerForClearFilter() {
        findViewById(R.id.clear_filter).setOnClickListener(view -> {
            cityFilter.setSelection(0);
            isGroundFilter.setSelection(0);
            isFavoriteFilter.setSelection(0);
            currentCity = cities[0];
            currentIsGround = groundOptions[0];
            currentIsFavorite = favoriteOptions[0];
        });
    }

    public void changeRecyclerViewItem() {
        new Thread(() ->{
            if(currentIsGround == "全選") {
                if(currentIsFavorite == "全選") {
                    if(currentCity == "全選") {
                        ParkingLotDataBase.getInstance().parkingLotDao().getAll().forEach(parkingLot -> {
                            parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                        });
                    }
                    else {
                        String cityEnglish = translateCityToEnglish(currentCity);
                        ParkingLotDataBase.getInstance().parkingLotDao().getFilterCity(cityEnglish).forEach(parkingLot -> {
                            parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                        });
                    }
                }
                else {
                    boolean flag = false;
                    if(currentIsFavorite == "是") {
                        flag = true;
                    }
                    if(currentCity == "全選") {
                        ParkingLotDataBase.getInstance().parkingLotDao().getFilterIsFavorite(flag).forEach(parkingLot -> {
                            parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                        });
                    }
                    else {
                        String cityEnglish = translateCityToEnglish(currentCity);
                        ParkingLotDataBase.getInstance().parkingLotDao().getFilterCityAndIsFavorite(cityEnglish, flag).forEach(parkingLot -> {
                            parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                        });
                    }
                }
            }
            else {
                String ground = "%地下%";
                if(currentIsGround == "是") {
                    if(currentIsFavorite == "全選") {
                        if(currentCity == "全選") {
                            ParkingLotDataBase.getInstance().parkingLotDao().getFilterIsGround(ground).forEach(parkingLot -> {
                                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                            });
                        }
                        else {
                            String cityEnglish = translateCityToEnglish(currentCity);
                            ParkingLotDataBase.getInstance().parkingLotDao().getFilterCityAndIsGround(cityEnglish, ground).forEach(parkingLot -> {
                                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                            });
                        }
                    }
                    else {
                        boolean flag = false;
                        if(currentIsFavorite == "是") {
                            flag = true;
                        }
                        if(currentCity == "全選") {
                            ParkingLotDataBase.getInstance().parkingLotDao().getFilterFavoriteAndIsGround(flag, ground).forEach(parkingLot -> {
                                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                            });
                        }
                        else {
                            String cityEnglish = translateCityToEnglish(currentCity);
                            ParkingLotDataBase.getInstance().parkingLotDao().getFilterCityAndFavoriteAndIsGround(cityEnglish, ground, flag).forEach(parkingLot -> {
                                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                            });
                        }
                    }
                }
                else if(currentIsGround == "否") {
                    if(currentIsFavorite == "全選") {
                        if(currentCity == "全選") {
                            ParkingLotDataBase.getInstance().parkingLotDao().getFilterNotGround(ground).forEach(parkingLot -> {
                                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                            });
                        }
                        else {
                            String cityEnglish = translateCityToEnglish(currentCity);
                            ParkingLotDataBase.getInstance().parkingLotDao().getFilterCityAndNotGround(cityEnglish, ground).forEach(parkingLot -> {
                                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                            });
                        }
                    }
                    else {
                        boolean flag = false;
                        if(currentIsFavorite == "是") {
                            flag = true;
                        }
                        if(currentCity == "全選") {
                            ParkingLotDataBase.getInstance().parkingLotDao().getFilterFavoriteAndNotGround(flag, ground).forEach(parkingLot -> {
                                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                            });
                        }
                        else {
                            String cityEnglish = translateCityToEnglish(currentCity);
                            ParkingLotDataBase.getInstance().parkingLotDao().getFilterCityAndFavoriteAndNotGround(cityEnglish, ground, flag).forEach(parkingLot -> {
                                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
                            });
                        }
                    }
                }
            }
            getActivity().runOnUiThread(()->parkingLotRowAdapter.UpdateRecyclerView(parkingLotInfoDtos));
        }).start();
    }

    private String translateCityToEnglish(String city) {
        Map<String, String> cityChineseMap = new HashMap<>();
        cityChineseMap.put("臺北", "Taipei");
        cityChineseMap.put("基隆", "Keelung");
        cityChineseMap.put("桃園", "Taoyuan");
        cityChineseMap.put("新竹", "Hsinchu");
        cityChineseMap.put("花蓮", "HualienCounty");
        cityChineseMap.put("宜蘭", "YilanCounty");
        cityChineseMap.put("苗栗", "MiaoliCounty");
        cityChineseMap.put("臺中", "Taichung");
        cityChineseMap.put("南投", "NantouCounty");
        cityChineseMap.put("嘉義", "Chiayi");
        cityChineseMap.put("嘉義縣", "ChiayiCounty");
        cityChineseMap.put("臺南", "Tainan");
        cityChineseMap.put("高雄", "KaohsIung");
        cityChineseMap.put("屏東", "PingtungCounty");
        cityChineseMap.put("臺東", "TaitungCounty");
        cityChineseMap.put("金門", "KinmenCounty");
        cityChineseMap.put("連江縣", "LienchiangCounty");

        return cityChineseMap.get(city);
    }

    // 设置最大宽度，看需要而定，
    @Override
    protected int getMaxWidth() {
        return super.getMaxWidth();
    }
    // 设置最大高度，看需要而定
    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }
    // 设置自定义动画器，看需要而定
    @Override
    protected PopupAnimator getPopupAnimator() {
        return super.getPopupAnimator();
    }
    /**
     * 弹窗的宽度，用来动态设定当前弹窗的宽度，受getMaxWidth()限制
     *
     * @return
     */
    protected int getPopupWidth() {
        return 0;
    }

    /**
     * 弹窗的高度，用来动态设定当前弹窗的高度，受getMaxHeight()限制
     *
     * @return
     */
    protected int getPopupHeight() {
        return 0;
    }
}
