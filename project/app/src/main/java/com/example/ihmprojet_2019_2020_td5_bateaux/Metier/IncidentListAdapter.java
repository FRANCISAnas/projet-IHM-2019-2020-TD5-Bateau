package com.example.ihmprojet_2019_2020_td5_bateaux.Metier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;

import java.util.ArrayList;

public class IncidentListAdapter extends ArrayAdapter {

    private Context mContext;
    int nRessource;
    ArrayList<Incident> items;
    public IncidentListAdapter( Context context, int resource,  ArrayList<Incident> objects) {
        super(context, resource, objects);
        mContext=context;
        nRessource=resource;
        items = objects;

    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        String nature  = items.get(position).getNature();
        String date  = items.get(position).getDate();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(nRessource,parent,false);


        TextView textView = (TextView) convertView.findViewById(R.id.date);
        TextView textView2 = (TextView) convertView.findViewById(R.id.Description);
        textView.setText(nature);
        textView2.setText(date);


        return convertView;

    }
}
