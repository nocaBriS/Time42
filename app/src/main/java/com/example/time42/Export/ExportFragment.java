package com.example.time42.Export;

import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.time42.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ExportFragment extends Fragment {

    EditText etDateRange;
    MaterialDatePicker mdp;
    static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
    Date startDate = null;
    Date endDate = null;

    Button btnExport;

    public ExportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_export, container, false);
        etDateRange = root.findViewById(R.id.etDatePicker);

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
                Date startDate = new Date(selection.first);
                Date endDate = new Date(selection.second);

                etDateRange.setText(sdf.format(startDate) + " - " + sdf.format(endDate));

            }
        });

        btnExport = root.findViewById(R.id.btnExport);
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return root;
    }

}