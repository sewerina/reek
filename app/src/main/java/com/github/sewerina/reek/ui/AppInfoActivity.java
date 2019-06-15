package com.github.sewerina.reek.ui;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.sewerina.reek.R;

public class AppInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        TextView appInfo = findViewById(R.id.tv_appInfo);
        appInfo.setText(Html.fromHtml(getString(R.string.tv_appInfo)));
    }
}
