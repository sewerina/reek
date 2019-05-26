package com.github.sewerina.reek;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ReekApp extends Application {
    private static ViewModelProvider.Factory sViewModelFactory;

    @Override
    public void onCreate() {
        super.onCreate();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        sViewModelFactory = new AppViewModelFactory(requestQueue);
    }

    public static ViewModelProvider.Factory getViewModelFactory(){
        if (sViewModelFactory == null){
            throw new IllegalStateException("Application is not initialized");
        }
        return sViewModelFactory;
    }
}
