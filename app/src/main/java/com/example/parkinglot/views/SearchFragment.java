package com.example.parkinglot.views;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.parkinglot.entity.ParkingLotEntity;
import com.lxj.xpopup.XPopup;
import com.example.parkinglot.dto.ParkingLotInfoDto;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements ParkingLotRowAdapter.UserClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;

    Toolbar toolbar;

    List<ParkingLotInfoDto> parkingLotInfoDtos = new ArrayList<>();

    ParkingLotRowAdapter parkingLotRowAdapter;

    RecyclerView recyclerView;


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.parkingLotList);
        recyclerView.setAdapter(parkingLotRowAdapter);
        toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("搜尋");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllParkingLotName();
    }

    @Override
    public void onStart() {
        super.onStart();
        prepareRecycleView();
    }

    public void getAllParkingLotName(){
        new Thread(() ->{
            ParkingLotDataBase.getInstance().parkingLotDao().getAll().forEach(parkingLot -> {
                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
            });
            getActivity().runOnUiThread(() -> preAdapter());
        }).start();
    }

    public void prepareRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void preAdapter(){
        parkingLotRowAdapter = new ParkingLotRowAdapter(parkingLotInfoDtos, this.getContext(), this);
        recyclerView.setAdapter(parkingLotRowAdapter);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.nav_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_View);
        MenuItem filter = menu.findItem(R.id.filter);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String searchStr = s;
                parkingLotRowAdapter.getFilter().filter(s);
                return false;
            }
        });
        filter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                new XPopup.Builder(getContext())
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .isViewMode(true)
                        .asCustom(new CenterPopup(getContext(), parkingLotRowAdapter))
                        .show();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public String translateCityToChinese(String city) {
        Map<String, String> cityChineseMap = new HashMap<>();
        cityChineseMap.put("Taipei", "臺北");
        cityChineseMap.put("Keelung", "基隆");
        cityChineseMap.put("Taoyuan", "桃園");
        cityChineseMap.put("Hsinchu", "新竹");
        cityChineseMap.put("HualienCounty", "花蓮");
        cityChineseMap.put("YilanCounty", "宜蘭");
        cityChineseMap.put("MiaoliCounty", "苗栗");
        cityChineseMap.put("Taichung", "臺中");
        cityChineseMap.put("NantouCounty", "南投");
        cityChineseMap.put("Chiayi", "嘉義");
        cityChineseMap.put("ChiayiCounty", "嘉義縣");
        cityChineseMap.put("Tainan", "臺南");
        cityChineseMap.put("KaohsIung", "高雄");
        cityChineseMap.put("PingtungCounty", "屏東");
        cityChineseMap.put("TaitungCounty", "臺東");
        cityChineseMap.put("KinmenCounty", "金門");
        cityChineseMap.put("LienchiangCounty", "連江縣");

        return cityChineseMap.get(city);
    }

    @Override
    public void selectedParkingLot(ParkingLotInfoDto parkingLotInfoDto) {
        new XPopup.Builder(getContext())
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .isViewMode(true)
                .asCustom(new PagerBottomPopup(getContext(), parkingLotInfoDto, parkingLotRowAdapter))
                .show();
//        Toast.makeText(this.getContext(), "停車場名字為" + parkingLotInfoDto.getParkingLotName(), Toast.LENGTH_SHORT).show(); //有抓到正在選擇的停車場了
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}