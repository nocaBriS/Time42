package com.example.time42.Home;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.time42.ProjectList.ProjectListViewModel;
import com.example.time42.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView nameView;

    private Spinner prkSpinner;
    private Spinner timeSpinner;

    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = getActivity().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        nameView = root.findViewById(R.id.nameView);
        nameView.setText(sharedPreferences.getString("full name", "Name"));

        prkSpinner = root.findViewById(R.id.prkSpinner);
        timeSpinner = root.findViewById(R.id.timeSpinner);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getAllProject().observe(getViewLifecycleOwner(), list -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            prkSpinner.setAdapter(adapter);


        });

            prkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    LocalDate today = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yy");
                    Log.i("Date", (String) today.format(formatter));

                    HomeGetTimeViewModel getTimeModel = new ViewModelProvider(this).get(HomeGetTimeViewModel.class);
                    getTimeModel.getAllProject().observe(getViewLifecycleOwner(), list -> {

                    });

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

    }

    public void changeGraph() {

    }

}