package com.example.time42.Export;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.time42.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ExportFragment extends Fragment {

    EditText etDateRange;
    MaterialDatePicker mdp;
    static SimpleDateFormat sdfShown = new SimpleDateFormat("dd. MMMM yyyy");
    static SimpleDateFormat sdfDB = new SimpleDateFormat("dd MM yy");
    Date startDate = null;
    Date endDate = null;


    HashMap<String, HashMap<String, ArrayList<String>>> times = new HashMap<>();
    private String data;

    Button btnExport;
    TextView tvTest;

    SharedPreferences sharedpreferences;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ExportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_export, container, false);
        etDateRange = root.findViewById(R.id.etDatePicker);

        tvTest = root.findViewById(R.id.tvTest);

        //dp = root.findViewById(R.id.datePicker);
        //dp.setVisibility(View.VISIBLE);

        MaterialDatePicker.Builder<Pair<Long, Long>> materialDatePickerBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDatePickerBuilder.setTitleText("Wähle Zeitraum");
        materialDatePickerBuilder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);
        mdp = materialDatePickerBuilder.build();

        etDateRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdp.show(getFragmentManager(), "MATERIAL_DATE_PICKER");

            }
        });

        mdp.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override public void onPositiveButtonClick(Pair<Long,Long> selection) {
                startDate = new Date(selection.first);
                endDate = new Date(selection.second);

                etDateRange.setText(sdfShown.format(startDate) + " - " + sdfShown.format(endDate));

            }
        });

        btnExport = root.findViewById(R.id.btnExport);
        btnExport.setOnClickListener(v -> btnExportClicked(v));

        sharedpreferences = this.getActivity().getSharedPreferences("logPref", Context.MODE_PRIVATE);

        return root;
    }

    private void btnExportClicked(View v) {
        CollectionReference colRef = db.collection("User").document(sharedpreferences.getString("name", null)).collection("Time");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() == null) {
                        //Create Collection

                    } else {
                        //find all Calendar Dates
                        Log.i("ExportFragment", "find CalenderDates");
                        //Task<QuerySnapshot> task = db.collection("User").document(sharedpreferences.getString("name", null)).collection("Time").get();
                        //QuerySnapshot qSnapshot = colRef.get().getResult();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Date tempStart = startDate;
                            HashMap<String, ArrayList<String>> timesDoc = new HashMap<>();

                            while (tempStart.getTime() <= endDate.getTime()) {
                                Log.i("ExportFragment", document.getId());

                                timesDoc.put(sdfDB.format(tempStart), (ArrayList<String>) document.get(sdfDB.format(tempStart)));
                                times.put(document.getId(), timesDoc);
                                        //(sdfDB.format(tempStart), (ArrayList<String>) document.get(sdfDB.format(tempStart)));
                                tvTest.setText(times.toString());

                                Calendar c  = Calendar.getInstance();
                                c.setTime(tempStart);
                                c.add(Calendar.DATE, 1);
                                tempStart = c.getTime();
                            }
                            exportData();
                        }
                    }
                }
            }
        });

    }

    private void exportData() {
        //projname;date;time

        data = "ProjectName;Date;hh.ss \n";


        for(Map.Entry timesME: times.entrySet()) {
            //key - String
            //value - hashmap<String, arraylist<string>>
            HashMap<String, ArrayList<String>> timesPerProject = (HashMap<String, ArrayList<String>>) timesME.getValue();
            Iterator iterator = timesPerProject.entrySet().iterator();

            while(iterator.hasNext()) {
                //key - string
                //value - arraylist<string>
                Map.Entry timesPerProjectME = (Map.Entry) iterator.next();
                ArrayList<String> timesPerProjectAL = (ArrayList<String>) timesPerProjectME.getValue();
                if(timesPerProjectAL != null) {
                    for (String s : timesPerProjectAL) {

                        DocumentReference docRef = db.collection("Project").document(timesME.getKey().toString());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()) {

                                    //data += task.getResult().get("Name").toString() + ";" + timesPerProjectME.getKey() + ";" + s + "\n";
                                    writeForFile(task.getResult().get("Name").toString(), timesPerProjectME.getKey().toString(), s);
                                }
                                Log.d("ExportFragment", data);
                                tvTest.setText(data);
                                writeInFile(data);
                            }
                        });
                    }
                }
            }
        }

    }



    private void writeForFile(String projName, String date, String time) {
        Log.d("ExportFragment", projName + date + time);
        data += projName + ";" + date + ";" + time + "\n";
        Log.d("ExportFragment", data);
    }

    private void writeInFile(String data) {

    }

}