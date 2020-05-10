package com.example.ihmprojet_2019_2020_td5_bateaux.Metier;

import android.content.Context;

import android.graphics.Color;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;

import java.util.ArrayList;

public class IncidentListAdapter extends ArrayAdapter {

    int nRessource;
    ArrayList<Incident> items;
    private Context mContext;

    public IncidentListAdapter(Context context, int resource, ArrayList<Incident> objects) {
        super(context, resource, objects);
        mContext = context;
        nRessource = resource;
        items = objects;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String nature = items.get(position).getNature();
        String date = items.get(position).getDate();
        String android_id = items.get(position).getAndroid_id();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(nRessource, parent, false);


        TextView textView =  convertView.findViewById(R.id.date);
        TextView textView2 =  convertView.findViewById(R.id.Description);
        textView.setText(nature);
        textView2.setText(date);
        if(android_id.equals(Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID))) {
            textView.setTextColor(Color.YELLOW);
            //textView2.setTextColor(Color.BLUE);

        }



        return convertView;

    }
}
