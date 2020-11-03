package com.example.time42.Project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.time42.Object.Project;

import java.util.ArrayList;

public class ProjectViewModel extends ViewModel {

    ArrayList<Project> list = new ArrayList<>();

    Project tmp1 = new Project("Time42", "15.10.2020", "15.04.2021");
    Project tmp2 = new Project("Test", "15.10.2020", "15.04.2021");

    private MutableLiveData<ArrayList<Project>> mObj;

    public ProjectViewModel() {

        list.add(tmp1);
        list.add(tmp2);

        mObj = new MutableLiveData<>();
        mObj.setValue(list);
    }

    public LiveData<ArrayList<Project>> getProject() {
        return mObj;
    }
}