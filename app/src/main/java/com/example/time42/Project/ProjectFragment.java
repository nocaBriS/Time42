package com.example.time42.Project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.time42.Object.Project;
import com.example.time42.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends Fragment {

    private ProjectViewModel projectViewModel;

    private List<Project> items;
    private ListView mListView;

    View root;

    SharedPreferences sharedPreferences;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        root = inflater.inflate(R.layout.fragment_project, container, false);
        mListView = root.findViewById(R.id.list);

        // Create the observer which updates the UI.
        final Observer<ArrayList<Project>> nameObserver = s -> {
            // Update the UI, in this case, a TextView.
            items = s;
            bindAdapterToListView(mListView);
        };
        projectViewModel.getProject().observe(getViewLifecycleOwner(), nameObserver);

        mListView.setOnItemClickListener((parent, view, position, id) -> expand(view));

        return root;
    }

    private void expand(View v) {
        ImageButton btn = v.findViewById(R.id.expandButton);
        ConstraintLayout expandableView = v.findViewById(R.id.expandView);
        CardView cardview = v.findViewById(R.id.cardview);

        if (expandableView.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(cardview, new AutoTransition());
            expandableView.setVisibility(View.VISIBLE);
            btn.setImageResource(R.drawable.ic_expand_less);
        } else {
            TransitionManager.beginDelayedTransition(cardview, new AutoTransition());
            expandableView.setVisibility(View.GONE);
            btn.setImageResource(R.drawable.ic_expand_more);
        }
    }

    private void bindAdapterToListView(ListView lv) {
        lv.setAdapter(new ProjectAdapter(this.getContext(),
                R.layout.listitem_project,
                items));
    }

}