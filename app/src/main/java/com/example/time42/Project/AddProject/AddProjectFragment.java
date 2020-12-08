package com.example.time42.Project.AddProject;

import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.time42.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProjectFragment extends Fragment {

    private AddProjectViewModel mViewModel;

    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_addproject, container, false);

        EditText startDate = root.findViewById(R.id.editTextStartDate);
        EditText endDate = root.findViewById(R.id.editTextEndDate);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT A DATE");
        builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        startDate.setOnClickListener(v ->
                materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER")
                );

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            Long stDate = selection.first;
            Long enDate = selection.second;

            @SuppressLint("SimpleDateFormat")
            String stdateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(stDate));
            @SuppressLint("SimpleDateFormat")
            String endateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(enDate));

            startDate.setText(stdateString);
            endDate.setText(endateString);

        });

        return root;
    }



}