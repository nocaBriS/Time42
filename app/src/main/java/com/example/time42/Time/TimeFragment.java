package com.example.time42.Time;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.time42.R;

import org.w3c.dom.Text;

public class TimeFragment extends Fragment {

    private TimeViewModel mViewModel;

    int id;

    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_time, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id", -1);
        }

        mViewModel = new ViewModelProvider(this, new TimeViewModelFactory(this.getActivity().getApplication(), id)).get(TimeViewModel.class);

        mViewModel.getmDate().observe(getViewLifecycleOwner(), date -> {

            TextView WeekDay = root.findViewById(R.id.WochentagTextView);
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
            WeekDay.setText(dayOfTheWeek);

            TextView dateText = root.findViewById(R.id.DateTextView);
            String dat          = (String) DateFormat.format("dd MMM yyyy",   date); // 20 Jun 2020
            dateText.setText(dat);


        });

        return root;
    }

}