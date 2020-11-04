package com.example.time42.Calendar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.time42.Object.testCalender;
import com.example.time42.R;

import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {

    private CalendarViewModel mViewModel;

    private List<testCalender> items;
    private ListView mListView;

    Calendar calendar;
    CalendarView calendarView;

    View root;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        root = inflater.inflate(R.layout.fragment_calender, container, false);
        mListView = root.findViewById(R.id.calenderList);

        calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2012);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);

        calendarView = root.findViewById(R.id.calendarView2);

        mViewModel.getProject().observe(getViewLifecycleOwner(), (Observer<List<testCalender>>) s -> {

            items = s;
            bindAdapterToListView(mListView);

        });

        return root;
    }

    private void bindAdapterToListView(ListView lv)
    {
        lv.setAdapter(new CalendarAdapter(this.getContext(),
                R.layout.listitem_calender,
                items));
    }

}