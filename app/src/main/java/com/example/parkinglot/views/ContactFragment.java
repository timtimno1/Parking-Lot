package com.example.parkinglot.views;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkinglot.ParkingLotDataBase;
import com.example.parkinglot.R;
import com.example.parkinglot.dto.ParkingLotInfoDto;
import com.example.parkinglot.entity.ParkingLotEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;

    String feedBackName;

    String feedBackEmail;

    String feedBackMessage;

    List<ParkingLotInfoDto> parkingLotInfoDtos = new ArrayList<>();

    List<String> parkingLotsName = new ArrayList<>();

    Dialog dialog;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
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
        getAllParkingLotName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        EditText name = view.findViewById(R.id.feedBackName);
        EditText email = view.findViewById(R.id.feedBackEmail);
        EditText message = view.findViewById(R.id.feedBackMessage);
        TextView textView = view.findViewById(R.id.contact_parkingLots);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(1200, 1600);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText = dialog.findViewById(R.id.select_parkingLot_dropdown);
                ListView listView = dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, parkingLotsName);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        textView.setText(adapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });

        Button button = view.findViewById(R.id.subnitFeedback);
        button.setOnClickListener(view1 -> {
            feedBackName = name.getText().toString();
            feedBackEmail = email.getText().toString();
            feedBackMessage = message.getText().toString();
            name.setText(null);
            email.setText(null);
            message.setText(null);
            textView.setText(null);
            Toast.makeText(getContext(), "感謝您的回報，我們會盡快處理", Toast.LENGTH_LONG).show();
        });
        return view;
    }

    public void getAllParkingLotName() {
        new Thread(() ->{
            ParkingLotDataBase.getInstance().parkingLotDao().getAll().forEach(parkingLot -> {
                parkingLotInfoDtos.add(ParkingLotEntity.toParkingLotInfoDto(parkingLot));
            });
            getAllParkingLotsName();

        }).start();
    }

    public void getAllParkingLotsName() {
        for(int i = 0; i < parkingLotInfoDtos.size(); i++) {
            parkingLotsName.add(parkingLotInfoDtos.get(i).getParkingLotName());
        }
    }
}
