package com.example.time42.Time;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TimeViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mid;


    public TimeViewModelFactory(Application application, String param) {
        mApplication = application;
        mid = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TimeViewModel(mApplication, mid);

    }
}
