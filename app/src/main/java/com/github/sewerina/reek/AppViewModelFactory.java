package com.github.sewerina.reek;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;

public class AppViewModelFactory implements ViewModelProvider.Factory {

    private final RequestQueue mRequestQueue;

    public AppViewModelFactory(RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(MainViewModel.class)) {
            return (T) new MainViewModel();
        }
        if (modelClass.equals(MapViewModel.class)) {
            return (T) new MapViewModel(mRequestQueue);
        }

        throw new IllegalArgumentException();
    }
}
