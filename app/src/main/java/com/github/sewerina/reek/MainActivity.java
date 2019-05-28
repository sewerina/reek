package com.github.sewerina.reek;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    private Spinner mSpinner;
    private Button mSelectLocationBtn;
    private Button mSendComplaintBtn;

    private ReekSpinnerAdapter mSpinnerAdapter;

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] reeks = {"Гарь",
                "Запах канализации",
                "Сероводород/Запах тухлых яиц",
                "Зловоние от свалки мусора",
                "Выхлопы от автомобилей"};

        mSpinner = findViewById(R.id.spinner);
        mSelectLocationBtn = findViewById(R.id.btn_selectLocation);
        mSendComplaintBtn = findViewById(R.id.btn_sendComplaint);

        mSpinnerAdapter = new ReekSpinnerAdapter(this, R.layout.reek_row, reeks);
        mSpinner.setAdapter(mSpinnerAdapter);

        mSendComplaintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "";
                String subject = "Жалоба на неприятный запах";
                String body = "";
                String chooserTitle = "Выберите почтовый клиент";
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intent, chooserTitle));
            }
        });

        mViewModel = ViewModelProviders.of(this, ReekApp.getViewModelFactory()).get(MainViewModel.class);


//        startActivity(new Intent(this, MapsActivity.class));

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    class ReekSpinnerAdapter extends ArrayAdapter<String> {
        private String[] mReeks;

        public ReekSpinnerAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
            super(context, resource, objects);
            mReeks = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        private View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.reek_row, parent, false);
            TextView label = row.findViewById(R.id.tv_reek);
            label.setText(mReeks[position]);

//            ImageView icon = (ImageView) row.findViewById(R.id.icon);
//            if (dayOfWeek[position] == "Котопятница"
//                    || dayOfWeek[position] == "Субкота") {
//                icon.setImageResource(R.drawable.paw_on);
//            } else {
//                icon.setImageResource(R.drawable.ic_launcher);
//            }

            return row;
        }
    }
}
