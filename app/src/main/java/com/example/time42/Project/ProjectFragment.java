package com.example.time42.Project;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.time42.R;

public class ProjectFragment extends Fragment {

    private ProjectViewModel projectViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        projectViewModel =
                new ViewModelProvider(this).get(ProjectViewModel.class);
        View root = inflater.inflate(R.layout.project_fragment, container, false);
        final TextView textView = root.findViewById(R.id.project_text);
        projectViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}