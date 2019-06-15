package com.github.sewerina.reek.model;

public class ReekMarker {
    public final String mName;
    public final double mLatitude;
    public final double mLongitude;

    public ReekMarker(String name, double latitude, double longitude) {
        mName = name;
        mLatitude = latitude;
        mLongitude = longitude;
    }
}
