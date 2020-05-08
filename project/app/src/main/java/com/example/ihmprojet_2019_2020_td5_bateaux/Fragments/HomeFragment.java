package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.ihmprojet_2019_2020_td5_bateaux.DirectionsActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.HomeActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class HomeFragment extends Fragment {
    private final static String TAG = "FRANCIS" ;

    public HomeFragment(){
        Log.d(TAG, "Home Fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        return rootView;
    }

}
