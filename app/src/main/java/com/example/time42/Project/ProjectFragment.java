package com.example.time42.Project;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.time42.Object.Project;
import com.example.time42.R;
import com.example.time42.Time.TimeFragment;

import java.util.List;

public class ProjectFragment extends Fragment {

    private ProjectViewModel projectViewModel;

    private List<Project> items;
    private ListView mListView;

    View root;

    SharedPreferences sharedPreferences;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_project, container, false);

        mListView = root.findViewById(R.id.list);
        ProgressBar progBar = root.findViewById(R.id.progressBar);

        progBar.setVisibility(View.VISIBLE);

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        projectViewModel.getProject().observe(getViewLifecycleOwner(), list -> {
            items = list;
            bindAdapterToListView(mListView);
            progBar.setVisibility(View.GONE);
        });

        //mListView.setOnItemClickListener((parent, view, position, id) -> test(view, position));
        mListView.setOnItemClickListener((parent, view, position, id) -> {

            Bundle bundle = new Bundle();
            bundle.putInt("id", items.get(position).getId());

            TimeFragment myFragment =  new TimeFragment();
            myFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, myFragment, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null) // name can be null
                            .commit();

                }
        );


        return root;
    }

    private void test(View v, int pos) {

        Log.i("ProjectFragment", items.get(pos).getId() + ": id");
        Navigation.createNavigateOnClickListener(R.id.nav_time, null);

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