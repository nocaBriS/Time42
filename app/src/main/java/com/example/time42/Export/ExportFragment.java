package com.example.time42.Export;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.time42.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;


public class ExportFragment extends Fragment {

    EditText etVon;
    EditText etBis;
    MaterialDatePicker mdp;

    public ExportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_export, container, false);
        etVon = root.findViewById(R.id.etVon);
        etBis = root.findViewById(R.id.etBis);


        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        buildDatePicker(materialDateBuilder);

        etVon.setOnClickListener(v -> openDatePicker(v, true));
        etBis.setOnClickListener(v -> openDatePicker(v, false));

        return root;
    }

    private void openDatePicker(View v, boolean isVon) {
        mdp.show(getFragmentManager(), "MATERIAL_DATE_PICKER");
            mdp.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                @Override
                public void onPositiveButtonClick(Object selection) {
                    if(isVon) {
                        etVon.setText(mdp.getHeaderText());
                    } else {
                        etBis.setText(mdp.getHeaderText());
                    }
                }
            });

    }

    private void buildDatePicker(MaterialDatePicker.Builder builder) {
        builder.setTitleText("Select Date");
        mdp = builder.build();
    }

}