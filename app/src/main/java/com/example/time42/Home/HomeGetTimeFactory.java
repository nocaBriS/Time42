package com.example.time42.Home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class HomeGetTimeFactory implements ViewModelProvider.Factory {
    private final Application mApplication;
    private final String mId;
    private final int mTime;

    public HomeGetTimeFactory(Application application, String id, int time) {
        mApplication = application;
        mId = id;
        mTime = time;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeGetTimeViewModel(mApplication, mTime);

    }


}
