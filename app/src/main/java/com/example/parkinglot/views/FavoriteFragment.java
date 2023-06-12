package com.example.parkinglot.views;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.R;
import com.example.parkinglot.dto.ParkingLotInfoDto;
import com.example.parkinglot.entity.ParkingLotEntity;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment implements ParkingLotRowAdapter.UserClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    Toolbar toolbar;

    List<ParkingLotInfoDto> parkingLotInfoDtos = new ArrayList<>();

    ParkingLotRowAdapter userAdapter;

    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllFavoriteParkingLots();
    }

    @Override
    public void onStart() {
        super.onStart();
        prepareRecycleView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.parkingLotList);
        recyclerView.setAdapter(userAdapter);
//        toolbar = view.findViewById(R.id.toolbar2);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
//        activity.getSupportActionBar().setTitle("收藏");
        setHasOptionsMenu(true);
        return view;
    }

    public void getAllFavoriteParkingLots(){
        new Thread(() ->{
            parkingLotInfoDtos.clear();
            ParkingLotDataBase.getInstance().parkingLotDao().getFavoriteParkingLots().forEach(parkingLot -> {
                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
            });
            getActivity().runOnUiThread(() -> preAdapter());
        }).start();
    }

    private void updateParkingLotInfoDtos(List<ParkingLotInfoDto> parkingLotInfoDtos){
        this.parkingLotInfoDtos = parkingLotInfoDtos;
    }

    public void prepareRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void preAdapter(){
        if(userAdapter != null){
            userAdapter.parkingLotdtos = parkingLotInfoDtos;
            userAdapter.notifyDataSetChanged();
            return;
        }
        userAdapter = new ParkingLotRowAdapter(parkingLotInfoDtos, this.getContext(), this);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.nav_menu2, menu);
        MenuItem menuItem = menu.findItem(R.id.search_View2);
        MenuItem filter = menu.findItem(R.id.filter2);
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
                userAdapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void selectedParkingLot(ParkingLotInfoDto parkingLotInfoDto) {
        String[] content = {"test"};
        new XPopup.Builder(getContext())
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .isViewMode(true)
                .asCustom(new PagerBottomPopup(getContext(), parkingLotInfoDto))
                .show();
//        Toast.makeText(this.getContext(), "停車場名字為" + parkingLotInfoDto.getParkingLotName(), Toast.LENGTH_SHORT).show(); //有抓到正在選擇的停車場了
    }
}
