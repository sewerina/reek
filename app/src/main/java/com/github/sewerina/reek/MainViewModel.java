package com.github.sewerina.reek;

import android.text.TextUtils;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    public final RecipientList mRecipientList = new RecipientList();

    public MainViewModel() {
    }

    public void checkRecipient(int position, boolean isChecked) {
        mRecipientList.get(position).mIsSelected = isChecked;
    }

    public String email() {
        ArrayList<String> emails = new ArrayList<>();
        for(Recipient recipient : mRecipientList) {
            if (recipient.mIsSelected) {
                emails.add(recipient.mEmail);
            }
        }

        return TextUtils.join(",", emails);
    }
}
