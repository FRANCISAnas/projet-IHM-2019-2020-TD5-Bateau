package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;


public class PostFragment extends Fragment {


    public PostFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.incident_submission, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.natures);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(container.getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.natures));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(myAdapter);

        return rootView;
    }
}
