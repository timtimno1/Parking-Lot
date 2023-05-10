package com.example.parkinglot.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import com.example.parkinglot.R;
import com.example.parkinglot.entity.ParkingLotEntity;
import com.example.parkinglot.viewmodels.MapViewModel;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, View.OnClickListener/*, GoogleMap.OnInfoWindowClickListener*/ {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;

    private GoogleMap googleMap;

    private ClusterManager<MyItem> clusterManager;

    private MapView mapView;

    private Map<String, ParkingLotEntity> parkingLotEntitys;

    private MapViewModel mapViewModel = new MapViewModel();

    private static final String TAG = MapFragment.class.getSimpleName();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_map, container, false);
        mapView = view.findViewById(R.id.view_map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        //        mapViewModel.doAction();
        //        mapViewModel.getData().observe(getViewLifecycleOwner(), latLong -> {
        //            LatLng sydney = new LatLng(latLong.getLat(), latLong.getLon());
        ////            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        ////            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * <p>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(25.043383, 121.533264), 18.0f);
        googleMap.moveCamera(point);
        googleMap.animateCamera(point);

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this.requireContext(), R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        }
        catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.setOnMyLocationButtonClickListener(this);
        this.googleMap.setOnMyLocationClickListener(this);

        mapViewModel.doAction();
        mapViewModel.getData().observe(getViewLifecycleOwner(), this::addMarkerToMap);
        mapViewModel.getSyncMessage().observe(getViewLifecycleOwner(), syncMessage -> {
            // TODO 這邊是不是不應該寫邏輯
            // TODO 成功才更新圖標
            Snackbar.make(this.getView(), syncMessage, Snackbar.LENGTH_SHORT).show();
//            Toast.makeText(this.getContext(), syncMessage, Toast.LENGTH_SHORT).show();
            mapViewModel.doAction();
        });
        setUpCluster();

        // Set a listener for info window events.

//        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this.getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this.getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    /**
     *
     *   Handles the click event for the sync button.
     *
     *   @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        mapViewModel.doSync();
        Log.d("BUTTONS", "User tapped the sync");
    }

    private void addMarkerToMap(List<ParkingLotEntity> parkingLotEntities) {
        // TODO 刪除重複的marker
        clusterManager.clearItems();
        for (ParkingLotEntity parkingLotEntity : parkingLotEntities) {
            clusterManager.addItem(new MyItem(parkingLotEntity.latitude, parkingLotEntity.longitude, parkingLotEntity.parkingLotName, parkingLotEntity.phoneNumber));
            //            googleMap.addMarker(new MarkerOptions().position(new LatLng(parkingLot.latitude, parkingLot.longitude)).title(parkingLot.parkingLotName));
        }
        clusterManager.setRenderer(new MarkerClusterRenderer(this.getContext(), googleMap, clusterManager));
        clusterManager.cluster();

        // convert list to hashMap
//        this.parkingLotEntitys = parkingLotEntities.stream()
//                .collect(Collectors.toMap(ParkingLotEntity::getCarParkID, Function.identity()));
    }

    @SuppressLint("PotentialBehaviorOverride")
    private void setUpCluster() {
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = new ClusterManager<>(this.requireContext(), googleMap);
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
    }

//    @Override
//    public void onInfoWindowClick(Marker marker) {
//        Toast.makeText(this.requireContext(), parkingLotEntitys.get(marker.getTitle()).fareDescription, Toast.LENGTH_SHORT).show();
//    }

    public class MyItem implements ClusterItem {
        private final LatLng position;
        private final String title;
        private final String snippet;

        public MyItem(double lat, double lng, String title, String snippet) {
            position = new LatLng(lat, lng);
            this.title = title;
            this.snippet = snippet;
        }

        @Override
        public LatLng getPosition() {
            return position;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getSnippet() {
            return snippet;
        }
    }

    public class MarkerClusterRenderer extends DefaultClusterRenderer<MyItem> {   // 1

        private static final int MARKER_DIMENSION = 100;  // 2

        private final IconGenerator iconGenerator;
        private final ImageView markerImageView;

        public MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
            iconGenerator = new IconGenerator(context);  // 3
            markerImageView = new ImageView(context);
            markerImageView.setLayoutParams(new ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION));
            iconGenerator.setContentView(markerImageView);  // 4
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) { // 5
            markerImageView.setImageResource(R.drawable.parking);  // 6
            Bitmap icon = iconGenerator.makeIcon();  // 7
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));  // 8
            markerOptions.title(item.getTitle());
            markerOptions.snippet(item.getSnippet());
        }
    }
}
