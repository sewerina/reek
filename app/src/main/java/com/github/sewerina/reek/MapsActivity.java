package com.github.sewerina.reek;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String[] LOCATION_PERMISSIONS = new String[]
            {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;
    private GoogleMap mMap;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mViewModel = ViewModelProviders.of(this, ReekApp.getViewModelFactory()).get(MainViewModel.class);

        mViewModel.getReekObjects().observe(this, new Observer<List<ReekObject>>() {
            @Override
            public void onChanged(List<ReekObject> reekObjects) {
//                StringBuilder builder = new StringBuilder();
//
//                for (ReekObject reek : reekObjects) {
//                    builder.append(reek.mName).append("\n");
//                }
//                mTextView.setText(builder);

                showReeks(reekObjects);
            }
        });
    }

    private void showReeks(List<ReekObject> reekObjects) {
        if (mMap == null) {
            return;
        }

        for (ReekObject reek: reekObjects) {
            LatLng point = new LatLng(reek.mLatitude, reek.mLongitude);
            mMap.addMarker(new MarkerOptions().position(point).title(reek.mName));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            Log.d("MapsActivity", "showReeks: " + reek.mName);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        setMoscowLocation();

        if (hasLocationPermission()) {
            setCurrentLocation();
        } else {
            setMoscowLocation();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
            }
        }
        mViewModel.load();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
                    setCurrentLocation();
                }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private void setCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            @SuppressLint("MissingPermission") Location lastKnownLocation
                    = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng last = null;

            if (lastKnownLocation != null) {
                last = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            } else {
                last = new LatLng(55.742793, 37.615401);
            }

            mMap.addMarker(new MarkerOptions().position(last).title("Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(last));
        }
    }

    private void setMoscowLocation() {
        LatLng last = new LatLng(55.742793, 37.615401);
        mMap.addMarker(new MarkerOptions().position(last).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(last));
    }

    private boolean hasLocationPermission() {
        int result = ContextCompat
                .checkSelfPermission(this, LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
