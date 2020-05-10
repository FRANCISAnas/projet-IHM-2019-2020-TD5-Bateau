package com.example.ihmprojet_2019_2020_td5_bateaux;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.ihmprojet_2019_2020_td5_bateaux.MainActivity.*;
import static com.example.ihmprojet_2019_2020_td5_bateaux.MainActivity.currentLocation;

public class GPSFragment extends Fragment {
    private IGPSActivity igpsActivity; //able to move camera
    private TextView placeNameTextView;
    private Location currentLocation;
    //default constructor
    public GPSFragment() { }
    public GPSFragment(IGPSActivity activity) {igpsActivity=activity; }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView=inflater.inflate(R.layout.fragment_gps,container,false);
        placeNameTextView=rootView.findViewById(R.id.placeName);
        final ImageView imageGPSGranted=rootView.findViewById(R.id.imageGPSGranted);
        final ImageView imageGPSActivated=rootView.findViewById(R.id.imageGpsActivated);
        //check permission
        boolean permissionGranted =ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED;
        if (permissionGranted){
            imageGPSGranted.setImageResource(R.drawable.unlocked);
            LocationListener listner = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    currentLocation=location;
                    igpsActivity.moveCamera();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                    imageGPSActivated.setImageResource(R.drawable.unlocked);

                }

                @Override
                public void onProviderDisabled(String provider) {
                    imageGPSActivated.setImageResource(R.drawable.locked);

                }
            };
            LocationManager locationManager =(LocationManager)(getActivity().getSystemService(Context.LOCATION_SERVICE));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,1,listner);
            imageGPSActivated.setImageResource(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)? R.drawable.unlocked : R.drawable.locked);
        }else {
            imageGPSActivated.setImageResource(R.drawable.locked);
            imageGPSGranted.setImageResource(R.drawable.unlocked);
        }

        return rootView;
    }


    LatLng getPosition(){

        return new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
    }
    String getPlaceName() throws IOException{
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> adresses = geocoder.getFromLocation(currentLocation.getLatitude(),currentLocation.getLongitude(),1);
        return adresses.get(0).getLocality();
    }
    void setPlaceName(String placeName){
        placeNameTextView.setText(placeName);
    }


}
