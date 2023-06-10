package com.example.parkinglot.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkinglot.R;

import java.util.ArrayList;
import java.util.List;

import com.example.parkinglot.dto.ParkingLotInfoDto;

public class ParkingLotRowAdapter extends RecyclerView.Adapter<ParkingLotRowAdapter.UserAdapterVh> implements Filterable {

    public List<ParkingLotInfoDto> parkingLotdtos = new ArrayList<>();

    public List<ParkingLotInfoDto> getParkingLotdtosFilter = new ArrayList<>();

    public Context context;

    public UserClickListener userClickListener;
    public interface UserClickListener{
        void selectedParkingLot(ParkingLotInfoDto parkingLotInfoDto);
    }

    public ParkingLotRowAdapter(List<ParkingLotInfoDto> dtos, Context context, UserClickListener userClickListener){
        this.parkingLotdtos = dtos;
        this.getParkingLotdtosFilter = dtos;
        this.context = context;
        this.userClickListener = userClickListener;
    }

    @NonNull
    @Override
    public UserAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context1 = parent.getContext();
        View view = LayoutInflater.from(context1).inflate(R.layout.row_parkinglot, parent, false);
        return new UserAdapterVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterVh holder, int position) {
        ParkingLotInfoDto parkingLotInfoDto = parkingLotdtos.get(position);
        String parkingLot = parkingLotInfoDto.getParkingLotName();
        String city = parkingLotInfoDto.getCity();
        holder.parkingLotName.setText(parkingLot);
        holder.city.setText(city);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userClickListener.selectedParkingLot(parkingLotInfoDto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parkingLotdtos.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    filterResults.values = getParkingLotdtosFilter;
                    filterResults.count = getParkingLotdtosFilter.size();
                }
                else {
                    String searchStr = charSequence.toString();
                    List<ParkingLotInfoDto> lotDtos = new ArrayList<>();
                    for(ParkingLotInfoDto lotDto: getParkingLotdtosFilter){
                        if(lotDto.getCity().contains(searchStr)){
                            lotDtos.add(lotDto);
                        }
                        else if(lotDto.getParkingLotName().contains(searchStr)){
                            lotDtos.add(lotDto);
                        }
                    }
                    filterResults.values = lotDtos;
                    filterResults.count = lotDtos.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                parkingLotdtos = (List<ParkingLotInfoDto>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public static class UserAdapterVh extends RecyclerView.ViewHolder{

        private TextView parkingLotName;
        private TextView city;
        public UserAdapterVh(@NonNull View itemView) {
            super(itemView);
            parkingLotName = itemView.findViewById(R.id.parkingLot);
            city = itemView.findViewById(R.id.city);
        }
    }

    public void clear() {
        List<ParkingLotInfoDto> temp = new ArrayList<>();
        int size = this.getItemCount();
        if(size > 0) {
            for (int i = 0; i < size; i++) {
                temp.add(parkingLotdtos.get(i));
            }
        }
        
    }
}
