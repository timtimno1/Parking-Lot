package com.example.parkinglot.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkinglot.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dto.SearchedParkingLotDto;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserAdapterVh> implements Filterable {

    public List<SearchedParkingLotDto> parkingLotdtos = new ArrayList<>();

    public List<SearchedParkingLotDto> getParkingLotdtosFilter = new ArrayList<>();

    public Context context;

    public UserClickListener userClickListener;
    public interface UserClickListener{
        void selectedParkingLot(SearchedParkingLotDto searchedParkingLotDto);
    }

    public UserAdapter(List<SearchedParkingLotDto> dtos, Context context, UserClickListener userClickListener){
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
        SearchedParkingLotDto searchedParkingLotDto = parkingLotdtos.get(position);
        String parkingLot = searchedParkingLotDto.getParkingLotName();
        String city = searchedParkingLotDto.getCity();
        holder.parkingLotName.setText(parkingLot);
        holder.city.setText(city);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userClickListener.selectedParkingLot(searchedParkingLotDto);
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
                    List<SearchedParkingLotDto> lotDtos = new ArrayList<>();
                    for(SearchedParkingLotDto lotDto: getParkingLotdtosFilter){
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
                parkingLotdtos = (List<SearchedParkingLotDto>) filterResults.values;
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
}
