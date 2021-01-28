package com.example.time42.Home;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.time42.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView nameView;
    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = getActivity().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        nameView = root.findViewById(R.id.nameView);
        nameView.setText(sharedPreferences.getString("name", "Name"));

        final TextView textView = root.findViewById(R.id.textView7);
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

}