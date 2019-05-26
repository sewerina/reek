package com.github.sewerina.reek;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;

    private RequestQueue mRequestQueue;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.tv_text);

        mRequestQueue = Volley.newRequestQueue(this);
        mViewModel = ViewModelProviders.of(this, ReekApp.getViewModelFactory()).get(MainViewModel.class);

        mViewModel.getNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                StringBuilder builder = new StringBuilder();

                for (String str : strings) {
                    builder.append(str).append("\n");
                }

                mTextView.setText(builder);
            }
        });
        mViewModel.load();

        startActivity(new Intent(this, MapsActivity.class));

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
