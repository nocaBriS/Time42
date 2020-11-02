package com.example.time42.Project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.time42.R;

public class ProjectFragment extends Fragment {

    private ProjectViewModel projectViewModel;

    View root;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        root = inflater.inflate(R.layout.fragment_project, container, false);

        return root;
    }


}