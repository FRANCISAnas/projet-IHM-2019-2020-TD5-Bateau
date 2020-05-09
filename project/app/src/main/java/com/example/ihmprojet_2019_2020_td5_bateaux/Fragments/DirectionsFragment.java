package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.ihmprojet_2019_2020_td5_bateaux.DirectionsActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;

public class DirectionsFragment extends Fragment {
    public DirectionsFragment() {
        // Empty constructor required
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View rootView = inflater.inflate(R.layout.fragment_directions, container, false);
        Button btn = (Button) rootView.findViewById(R.id.go_to_Directions_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), DirectionsActivity.class);
                startActivity(in);
            }
        });
        return rootView;
    }
}
