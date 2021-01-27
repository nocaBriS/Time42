package com.example.time42.Project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.time42.Project.ProjectInfo.ProjectInfoFragment;
import com.example.time42.Time.TimeFragment;

public class ViewPageAdapter extends FragmentPagerAdapter {

    String id;

    public ViewPageAdapter(FragmentManager fm, String id) {
        super(fm);
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new ProjectInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            fragment.setArguments(bundle);
        } else if (position == 1) {
            fragment = new TimeFragment();

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Projekt";
        } else if (position == 1) {
            title = "Zeit";
        }
        return title;
    }
}