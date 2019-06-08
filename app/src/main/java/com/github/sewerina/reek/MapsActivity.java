package com.github.sewerina.reek;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String EXTRA_FILE_PATH = "filePath";
    public static final String EXTRA_CURRENT_ADDRESS = "currentAddress";
    private static final String[] LOCATION_PERMISSIONS = new String[]
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;
    private GoogleMap mMap;
    //    private FloatingActionButton mSaveFab;
    private ExtendedFloatingActionButton mSaveFab;
    private MapViewModel mViewModel;
    private Marker mCurrentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mSaveFab = findViewById(R.id.fab_save);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mViewModel = ViewModelProviders.of(this, ReekApp.getViewModelFactory()).get(MapViewModel.class);

        mViewModel.getIndustrialCompaniesMarkers().observe(this, new Observer<List<ReekMarker>>() {
            @Override
            public void onChanged(List<ReekMarker> reekMarkers) {
                showReeks(reekMarkers, R.drawable.icon_marker_red);
            }
        });

        mViewModel.getLargeWasteBinsMarkers().observe(this, new Observer<List<ReekMarker>>() {
            @Override
            public void onChanged(List<ReekMarker> reekMarkers) {
                showReeks(reekMarkers, R.drawable.icon_marker_orange);
            }
        });

        mSaveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureMapScreen();
            }
        });
    }

    private void showReeks(List<ReekMarker> reekMarkers, int drawResource) {
        if (mMap == null) {
            return;
        }

        BitmapDescriptor bitmapDesc = BitmapDescriptorFactory.fromResource(drawResource);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(bitmapDesc);
        for (ReekMarker marker : reekMarkers) {
            LatLng point = new LatLng(marker.mLatitude, marker.mLongitude);
            markerOptions.position(point).title(marker.mName);
            mMap.addMarker(markerOptions);
//            mMap.addMarker(new MarkerOptions().position(point).title(reek.mName));

//            mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            Log.d("MapsActivity", "showReeks: " + marker.mName);
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        setMoscowLocation();

        mMap.getUiSettings().setMapToolbarEnabled(false);

        if (hasLocationPermission()) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            setCurrentLocation();
        } else {
            setMoscowLocation();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
            }
        }

        mViewModel.loadIndustrialCompanies();
        mViewModel.loadLargeWasteBins();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mCurrentMarker != null) {
                    mCurrentMarker.remove();
                    mCurrentMarker = null;
                }
                BitmapDescriptor bitmapDesc = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_blue);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng)
                        .icon(bitmapDesc)
                        .title("Здесь");
                mCurrentMarker = mMap.addMarker(markerOptions);

//                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
//                try {
//                    List<Address> addresses  = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                    String city = addresses.get(0).getLocality();
//                    String state = addresses.get(0).getAdminArea();
//                    String country = addresses.get(0).getCountryName();
//                    String postalCode = addresses.get(0).getPostalCode();
//                    String knownName = addresses.get(0).getFeatureName();
//
//                    Log.i("MapsActivity", "onMapClick: address = " + address);
//                    Log.i("MapsActivity", "onMapClick: city = " + city);
//                    Log.i("MapsActivity", "onMapClick: state = " + state);
//                    Log.i("MapsActivity", "onMapClick: country = " + country);
//                    Log.i("MapsActivity", "onMapClick: postalCode = " + postalCode);
//                    Log.i("MapsActivity", "onMapClick: knownName = " + knownName);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//                Toast.makeText(MapsActivity.this, "On map click", Toast.LENGTH_SHORT).show();
            }
        });

//        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//            @Override
//            public void onMapLongClick(LatLng latLng) {
//                Toast.makeText(MapsActivity.this, "On map long click", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
                    setCurrentLocation();
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private void captureMapScreen() {
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
            Bitmap bitmap;

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                // TODO Auto-generated method stub
                bitmap = snapshot;
                try {
                    File externalCacheDir = MapsActivity.this.getExternalCacheDir();
                    File tempFile = File.createTempFile("map" + System.currentTimeMillis(), ".png", externalCacheDir);
                    FileOutputStream out = new FileOutputStream(tempFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

                    Log.d("MapsActivity", "onSnapshotReady: " + bitmap.toString());

                    sendData(tempFile.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mMap.snapshot(callback);
    }

    private String currentAddress(LatLng currentLatLng) {
        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(currentLatLng.latitude, currentLatLng.longitude, 1);
            return addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendData(String filePath) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_FILE_PATH, filePath);

        if (mCurrentMarker != null && mCurrentMarker.getPosition() != null) {
            String address = currentAddress(mCurrentMarker.getPosition());
            if (address != null) {
                resultIntent.putExtra(EXTRA_CURRENT_ADDRESS, address);
            }
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @SuppressLint("MissingPermission")
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

//            BitmapDescriptor bitmapDesc = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_blue);
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(last)
//                    .icon(bitmapDesc)
//                    .title("Вы здесь");

//            mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(last, 18));

        }
    }

    private void setMoscowLocation() {
        LatLng last = new LatLng(55.742793, 37.615401);
//        mMap.addMarker(new MarkerOptions().position(last).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(last));
    }

    private boolean hasLocationPermission() {
        int result = ContextCompat
                .checkSelfPermission(this, LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
