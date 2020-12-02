package com.example.time42.Profile;

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

import com.example.time42.Object.User;
import com.example.time42.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView textVor = root.findViewById(R.id.nameEditText);
        TextView textUser = root.findViewById(R.id.usernameEditText);
        TextView textEmail = root.findViewById(R.id.emailEditText);
        TextView textPass = root.findViewById(R.id.editTextTextPassword);


        profileViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User s) {

                textVor.setText(s.getVorname());
                textUser.setText(s.getNachname());
                textEmail.setText(s.getEmail());
                textPass.setText(s.getPassword());

            }
        });
        return root;
    }

}