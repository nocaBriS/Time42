package com.example.time42.Project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.time42.Calendar.CalendarAdapter;
import com.example.time42.R;
import com.google.android.material.tabs.TabLayout;

public class ProjectFragment extends Fragment {

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPageAdapter viewPageAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project, container, false);

        viewPager = root.findViewById(R.id.viewpage);
        tabLayout = root.findViewById(R.id.tabLayout);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String id = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }
        viewPageAdapter = new ViewPageAdapter(getParentFragmentManager(), id);

        viewPager.setAdapter(viewPageAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        viewPageAdapter = new ViewPageAdapter(null, null);
        viewPager.setAdapter(null);
        tabLayout.setupWithViewPager(viewPager);
    }


}