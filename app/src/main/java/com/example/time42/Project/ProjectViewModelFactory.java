package com.example.time42.Project;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProjectViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mParam;

    public ProjectViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProjectViewModel(mApplication, mParam);

    }


}
