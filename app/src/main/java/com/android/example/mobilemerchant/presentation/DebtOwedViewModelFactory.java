package com.android.example.mobilemerchant.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DebtOwedViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private boolean mParam;


    public DebtOwedViewModelFactory(Application application, boolean param) {
        mApplication = application;
        mParam = param;
    }


    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DebtOwedViewModel(mApplication, mParam);
    }
}