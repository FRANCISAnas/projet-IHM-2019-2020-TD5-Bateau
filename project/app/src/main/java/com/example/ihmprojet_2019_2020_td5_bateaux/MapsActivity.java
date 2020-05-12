package com.example.ihmprojet_2019_2020_td5_bateaux;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements IGPSActivity, OnMapReadyCallback {
    private GPSFragment gpsFragment;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gpsFragment = (GPSFragment) getSupportFragmentManager().findFragmentById(R.id.gpsLocation);
        if (gpsFragment == null) {
            gpsFragment = new GPSFragment(this);
            FragmentTransaction gpsTransaction = getSupportFragmentManager().beginTransaction();
            gpsTransaction.replace(R.id.gpsLocation, gpsFragment);
            gpsTransaction.addToBackStack(null);
            gpsTransaction.commit();
        }
        NavigationFragment navigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation);
        if (navigationFragment == null) {
            navigationFragment = new NavigationFragment();
            FragmentTransaction navTransaction = getSupportFragmentManager().beginTransaction();
            navTransaction.replace(R.id.navigation, navigationFragment);
            navTransaction.addToBackStack(null);
            navTransaction.commit();
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

    }

    @Override
    public void moveCamera() {
        try {
            gpsFragment.setPlaceName("Position : " + gpsFragment.getPlaceName());
        } catch (IOException e) {
            gpsFragment.setPlaceName("Position  inconnue");
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsFragment.getPosition(), 15f));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantedResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantedResults.length > 0 && grantedResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "FINE_LOCATION granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            break;
        }
        gpsFragment = new GPSFragment(this);
        FragmentTransaction gpsTransaction = getSupportFragmentManager().beginTransaction();
        gpsTransaction.replace(R.id.gpsLocation, gpsFragment);
        gpsTransaction.addToBackStack(null);
        gpsTransaction.commit();
    }
}
