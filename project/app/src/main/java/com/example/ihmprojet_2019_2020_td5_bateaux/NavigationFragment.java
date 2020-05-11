package com.example.ihmprojet_2019_2020_td5_bateaux;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import static com.example.ihmprojet_2019_2020_td5_bateaux.IGPSActivity.REQUEST_CODE;

public class NavigationFragment  extends Fragment {
   public NavigationFragment (){}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_navigation , container,false);
        rootView.findViewById(R.id.buttonCheckPermission).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);;
                }

            }
        });
        rootView.findViewById(R.id.buttonSwitchOnOffGPS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast =Toast.makeText(getContext(),"You Can change now",Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.buttonSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast =Toast.makeText(getContext(),"Custom setting to this",Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",getActivity().getPackageName(),null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
