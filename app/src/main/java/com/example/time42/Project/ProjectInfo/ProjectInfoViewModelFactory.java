package com.example.time42.Project.ProjectInfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProjectInfoViewModelFactory implements ViewModelProvider.Factory {
    private final Application mApplication;
    private final String mParam;

    public ProjectInfoViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProjectInfoViewModel(mApplication, mParam);

    }


}
