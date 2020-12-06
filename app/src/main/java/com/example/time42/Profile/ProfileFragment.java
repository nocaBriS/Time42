package com.example.time42.Profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.time42.Object.User;
import com.example.time42.R;



public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    Switch sw;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView textVor = root.findViewById(R.id.nameEditText);
        TextView textUser = root.findViewById(R.id.usernameEditText);
        TextView textEmail = root.findViewById(R.id.emailEditText);
        TextView textPass = root.findViewById(R.id.editTextTextPassword);


        profileViewModel.getUser().observe(getViewLifecycleOwner(), s -> {
            textVor.setText(s.getVorname());
            textUser.setText(s.getNachname());
            textEmail.setText(s.getEmail());
            textPass.setText(s.getPassword());
        });

        ImageView image = root.findViewById(R.id.profilePictureView);

        profileViewModel.getImagePath().observe(getViewLifecycleOwner(), s -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(s, 0, s.length);
            image.setImageBitmap(bitmap);
        });

        sw = root.findViewById(R.id.darkThemeSwitch);
        sw.setOnClickListener(v -> themeswitch());

        return root;
    }

    public void themeswitch()
    {
        if(sw.isChecked())
        {
            getActivity().setTheme(android.R.style.Theme_Black);
        }
        else
        {
            getActivity().setTheme(android.R.style.Theme_Light);
        }
    }

}