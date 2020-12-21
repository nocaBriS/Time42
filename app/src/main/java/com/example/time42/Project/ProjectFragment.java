package com.example.time42.Project;

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

    private ProjectViewModel mViewModel;

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    View root;

    TextView projectName;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_project, container, false);

        String id = null;
        Bundle bundle = this.getArguments();
        if(bundle !=null) {
            id = bundle.getString("id");
        }
        projectName = root.findViewById(R.id.ProjektName);

        mViewModel = new ViewModelProvider(this, new ProjectViewModelFactory(this.getActivity().getApplication(), id)).get(ProjectViewModel.class);
        mViewModel.getProject().observe(getViewLifecycleOwner(), obj -> {

            projectName.setText(obj.getName());


        });

        return root;
    }

}