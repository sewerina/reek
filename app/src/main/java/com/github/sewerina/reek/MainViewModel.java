package com.github.sewerina.reek;

import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainViewModel extends ViewModel {

    private MutableLiveData<String> mMapScreenPath = new MutableLiveData<>();
    private String mCurrentAddress;
    public final RecipientList mRecipientList = new RecipientList();
    public final ReekKindList mReekKindList = new ReekKindList();
    private final String template = "Сегодня {date} я (Ваше Ф.И.О.), находясь по адресу (указать район) Москвы/Московской области (см. скрин карты), почувствовал сильный запах {reek}. В связи с этим прошу Вас принять меры по установлению источника данного запаха, провести мониторинг ПДК веществ в воздухе и контроль за соблюдением ПДВ загрязняющих веществ предприятий в указанном месте.";
    private int mSelectReekPosition;

    public MainViewModel() {
    }

    public LiveData<String> mapScreenPath() {
        return mMapScreenPath;
    }

    public void setMapScreenPath(String path) {
            mMapScreenPath.postValue(path);
    }

    public boolean hasMapScreen() {
        return mMapScreenPath.getValue() != null && !mMapScreenPath.getValue().isEmpty();
    }

    public Uri mapScreenUri() {
        return Uri.parse("file://" + mMapScreenPath.getValue());
    }

    public void checkRecipient(int position, boolean isChecked) {
        mRecipientList.get(position).mIsSelected = isChecked;
    }

    public String email() {
        ArrayList<String> emails = new ArrayList<>();
        for (Recipient recipient : mRecipientList) {
            if (recipient.mIsSelected) {
                emails.add(recipient.mEmail);
            }
        }

        return TextUtils.join(",", emails);
    }

    public String body() {
        String date = currentDate();
        String reek = mReekKindList.get(mSelectReekPosition).mEmailText;
        String result = template.replace("{date}", date).replace("{reek}", reek);

        if (hasCurrentAddress()) {
            Pattern pattern = Pattern.compile(", \\d{6}");
            Matcher matcher = pattern.matcher(mCurrentAddress);
            String currentAddress = matcher.replaceAll("");
            currentAddress = currentAddress.replace(", Россия", "");
            result = result.replace("(указать район) Москвы/Московской области", currentAddress);
        }

        if (!hasMapScreen()) {
            result = result.replace(" (см. скрин карты)", "");
        }

        return result;
    }

    private String currentDate() {
        // Var1
//        Time today = new Time(Time.getCurrentTimezone());
//        today.setToNow();
//
//        StringBuilder strBuilder = new StringBuilder();
//        strBuilder.append(today.monthDay).append('.')
//                .append((today.month + 1)).append('.')
//                .append(today.year)
//                .append(" в ")
//                .append(today.format("%k:%M"));
//        return strBuilder.toString();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            // Var2
            SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy в HH:mm");
            return sdf.format(new Date());
        } else {
            // Var3
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            String strMonth = String.format("%02d", month);
            int year = calendar.get(Calendar.YEAR);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            return String.valueOf(day) + '.' +
                    strMonth + '.' + year + "г." +
                    " в " +
                    hour + " ч. " +
                    min + " мин.";
        }

        // Var4
//        java.text.DateFormat dateFormat = DateFormat.getDateFormat();
//        dateFormat.format(new Date());
    }

    public void setSelectReekPosition(int position) {
        if (position >= 0) {
            mSelectReekPosition = position;
        }
    }

    public void setCurrentAddress(String currentAddress) {
        mCurrentAddress = currentAddress;
    }

    private boolean hasCurrentAddress() {
        return mCurrentAddress != null && !mCurrentAddress.isEmpty();
    }
}
