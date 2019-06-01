package com.github.sewerina.reek;

public class Recipient {
    public final String mEmail;
    public boolean mIsSelected;
    public final String mTitle;

    public Recipient(String title, String email) {
        mTitle = title;
        mEmail = email;
    }
}
