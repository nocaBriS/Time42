package com.example.time42.Project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.time42.Object.Project;
import com.example.time42.R;

import java.util.List;

public class ProjectFragment extends Fragment {

    private ProjectViewModel projectViewModel;

    private List<Project> items;
    private ListView mListView;

    View root;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        root = inflater.inflate(R.layout.fragment_project, container, false);

        mListView = root.findViewById(R.id.list);

        projectViewModel.getProject().observe(getViewLifecycleOwner(), (Observer<List<Project>>) s -> {

            items = s;
            bindAdapterToListView(mListView);

        });

        mListView.setOnItemClickListener((parent, view, position, id) -> Log.i("Itemclick","clicked " + view + " item"));

        return root;
    }

    private void bindAdapterToListView(ListView lv)
    {
        lv.setAdapter(new ProjectAdapter(this.getContext(),
                R.layout.list_item_project_small,
                items));
    }

}