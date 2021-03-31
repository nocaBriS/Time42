package com.example.time42.Time;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.time42.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedpreferences;

    String id;
    View root;
    Date date;

    TextView WeekDay;
    TextView dateText;
    TextView timeText;

    Button btn;

    TimePicker timepicker;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_time, container, false);

        WeekDay = root.findViewById(R.id.dayTextView);
        dateText = root.findViewById(R.id.DateTextView);
        timeText = root.findViewById(R.id.timeTextView);
        timepicker = root.findViewById(R.id.timePicker);

        btn = root.findViewById(R.id.button);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        timepicker.setIs24HourView(true);
        timepicker.setHour(0);
        timepicker.setMinute(0);
        btn.setOnClickListener(this::saveTime);

        sharedpreferences = getActivity().getSharedPreferences("logPref", Context.MODE_PRIVATE);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }

        TimeViewModel mViewModel = new ViewModelProvider(this, new TimeViewModelFactory(this.getActivity().getApplication(), id)).get(TimeViewModel.class);

        mViewModel.getmDate().observe(getViewLifecycleOwner(), d -> {
            date = d;
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
            WeekDay.setText(dayOfTheWeek);
            String dat = (String) DateFormat.format("dd MMM yyyy", date); // 20 Jun 2020
            dateText.setText(dat);
            String time = (String) DateFormat.format("hh:mm", date); // 7:30
            timeText.setText(time);

        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void saveTime(View view) {

        Map<String, Object> time = new HashMap<>();
        time.put((String) DateFormat.format("dd MM yy", date), Collections.emptyList());

        DocumentReference docRef = db.collection("User").document(sharedpreferences.getString("name", null)).collection("Time").document(id);
        docRef.get().addOnSuccessListener(DocumentSnapshot -> {

            if (DocumentSnapshot.exists()) {
                if (DocumentSnapshot.getBoolean( (String) DateFormat.format("dd MMM yyyy", date) ) != null) {

                    docRef.set(time)
                            .addOnSuccessListener(aVoid -> Log.i("test", "DocumentSnapshot successfully written!"))
                            .addOnFailureListener(e -> Log.i("test", "Error writing document", e));

                } else {
                    docRef.update((String) DateFormat.format("dd MM yy", date), FieldValue.arrayUnion(timepicker.getHour() + ":" + timepicker.getMinute()));

                }

            }

        });


    }


}