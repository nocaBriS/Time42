package com.example.time42.Calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.time42.Object.testCalender;

import java.util.ArrayList;

public class CalendarViewModel extends ViewModel {
    ArrayList<testCalender> list = new ArrayList<>();

    testCalender tmp1 = new testCalender("Geburtstag", "30.11.2002", "30.11.2002");


    private MutableLiveData<ArrayList<testCalender>> mObj;

    public CalendarViewModel() {

        list.add(tmp1);

        mObj = new MutableLiveData<>();
        mObj.setValue(list);
    }

    public LiveData<ArrayList<testCalender>> getProject() {
        return mObj;
    }
}