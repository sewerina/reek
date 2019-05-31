package com.github.sewerina.reek;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    private static final int MAP_REQUEST_CODE = 13;
    private Spinner mSpinner;
    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;
    private Button mSelectLocationBtn;
    private ImageView mMapScreenIv;
    private Button mSendComplaintBtn;
    private ReekSpinnerAdapter mSpinnerAdapter;
    private String mScreenPath;

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.checkBox1:
                    mViewModel.checkRecipient(0, isChecked);
                    break;

                case R.id.checkBox2:
                    mViewModel.checkRecipient(1, isChecked);
                    break;

                case R.id.checkBox3:
                    mViewModel.checkRecipient(2, isChecked);
                    break;
            }
        }
    };

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
        mCheckBox1 = findViewById(R.id.checkBox1);
        mCheckBox2 = findViewById(R.id.checkBox2);
        mCheckBox3 = findViewById(R.id.checkBox3);
        mSelectLocationBtn = findViewById(R.id.btn_selectLocation);
        mMapScreenIv = findViewById(R.id.iv_mapScreen);
        mSendComplaintBtn = findViewById(R.id.btn_sendComplaint);

        mSpinnerAdapter = new ReekSpinnerAdapter(this, R.layout.reek_row, reeks);
        mSpinner.setAdapter(mSpinnerAdapter);

        mCheckBox1.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mCheckBox2.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mCheckBox3.setOnCheckedChangeListener(mOnCheckedChangeListener);

        mSendComplaintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mViewModel.email();
                String subject = "Жалоба на неприятный запах";
                String body = "Сегодня (дата) я (Ваше Ф.И.О.), находясь в (указать район) районе Москвы/Московской области (см. фото), почувствовал сильный запах (вид запаха). В связи с этим прошу Вас принять меры по установлению источника данного запаха, провести мониторинг ПДК веществ в воздухе и контроль за соблюдением ПДВ загрязняющих веществ предприятий в указанном месте.";
                String chooserTitle = "Выберите почтовый клиент";

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);

                Uri screenPath = Uri.parse("file://" + mScreenPath);
                intent.putExtra(Intent.EXTRA_STREAM, screenPath);

                startActivity(Intent.createChooser(intent, chooserTitle));
            }
        });

        mSelectLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), MapsActivity.class), MAP_REQUEST_CODE);
            }
        });

        mViewModel = ViewModelProviders.of(this, ReekApp.getViewModelFactory()).get(MainViewModel.class);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAP_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String filePath = data.getStringExtra(MapsActivity.EXTRA_FILE_PATH);
            if (filePath != null && !filePath.isEmpty()) {
                Log.d("MainActivity", "onActivityResult: filePath = " + filePath);
                mScreenPath = filePath;
                showMapScreen(filePath);
            }
        }
    }

    private void showMapScreen(String filePath) {
        Bitmap screen = BitmapFactory.decodeFile(filePath);
        mMapScreenIv.setImageBitmap(screen);
        mMapScreenIv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    class ReekSpinnerAdapter extends ArrayAdapter<String> {
        private String[] mReeks;

        private int[] mImageResources = {R.drawable.gar, R.drawable.canalization,
                R.drawable.eggs, R.drawable.garbage, R.drawable.exhaust};

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
            ImageView image = row.findViewById(R.id.iv_reek);
            image.setImageResource(mImageResources[position]);
            return row;
        }
    }
}
