package com.example.time42.Home;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
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

import com.example.time42.Project.ProjectInfo.ProjectInfoViewModel;
import com.example.time42.Project.ProjectInfo.ProjectInfoViewModelFactory;
import com.example.time42.ProjectList.ProjectListViewModel;
import com.example.time42.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView nameView;

    private Spinner prkSpinner;
    private Spinner timeSpinner;

    //Graph
    BarChart barChart;
    ArrayList<BarEntry> entries;
    ArrayList<String> labels;
    BarData data;
    BarDataSet bardataset;

    SharedPreferences sharedpreferences;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    HomeFragment tmp = this;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sharedpreferences = getActivity().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        nameView = root.findViewById(R.id.nameView);
        nameView.setText(sharedpreferences.getString("full name", "Name"));

        prkSpinner = root.findViewById(R.id.prkSpinner);
        timeSpinner = root.findViewById(R.id.timeSpinner);
        barChart = root.findViewById(R.id.homeChart);

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

                sharedpreferences = tmp.getActivity().getSharedPreferences("logPref", Context.MODE_PRIVATE);
                String name = sharedpreferences.getString("name", null);
                String mid = prkSpinner.getSelectedItem().toString();
                LocalDate today = LocalDate.now();

                if (prkSpinner.getSelectedItem().toString() != null) {

                    DocumentReference timeRef = db.collection("User").document(name).collection("Time").document(mid.toString());
                    timeRef.get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {

                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        //Get Time

                                        entries = new ArrayList<>();
                                        labels = new ArrayList<>();

                                        for (LocalDate tmp = LocalDate.now(); tmp.isAfter(today.minusDays(7)); tmp = tmp.minusDays(1)) {


                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yy");
                                            String formattedString = tmp.format(formatter);

                                            if (document.get(formattedString) != null) {

                                                ArrayList list = (ArrayList) document.get(formattedString);


                                                for (int i = 0; i < list.size(); i++) {
                                                    int sum = 0;
                                                    String text = list.get(i).toString();

                                                    String[] splittime = text.split(";");
                                                    for (int y = 0; y < splittime.length; y++) {


                                                        String[] splithours = splittime[y].split(":");
                                                        try {
                                                            int h = Integer.parseInt(splithours[0]);
                                                            int m = Integer.parseInt(splithours[1]);
                                                            sum += (h * 60) + m;

                                                        } catch (Exception e) {

                                                        }


                                                    }


                                                    Log.i("Date", "sum: " + sum);

                                                    entries.add(new BarEntry(sum, i));

                                                    bardataset = new BarDataSet(entries, "Minuten");

                                                    labels.add(formattedString);


                                                }


                                            }


                                        }
                                        data = new BarData(labels, bardataset);

                                        barChart.setData(data);
                                        barChart.invalidate();
                                        barChart.setDescription("Desc");
                                        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                                    }
                                }
                            });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void changeGraph() {

    }

}