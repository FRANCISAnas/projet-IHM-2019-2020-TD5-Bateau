package com.example.ihmprojet_2019_2020_td5_bateaux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.IncidentsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.IncidentListAdapter;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.PostService;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.Service;

import java.util.ArrayList;

public class IncidentsActivity extends AppCompatActivity {

    public static ArrayList<Incident> incidentArrayList;
    public static boolean resume = false;

    PostService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidents);

        ListView listView = (ListView) findViewById(R.id.myListView);
        Service incidentService = new Service(this,listView); //getWindow().getDecorView().getRootView()
        incidentService.execute();

        //service = new PostService(this);
        //service.execute();
        //resume = true;

        /*Button actionButton = findViewById(R.id.addIncident);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncidentsActivity.this, IncidentsFragment.class);
            }
        });*/



    }



}
