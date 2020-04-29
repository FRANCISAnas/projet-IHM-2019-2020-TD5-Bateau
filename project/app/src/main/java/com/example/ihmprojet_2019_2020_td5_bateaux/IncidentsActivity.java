package com.example.ihmprojet_2019_2020_td5_bateaux;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentGetService;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentPostService;

import java.util.ArrayList;

public class IncidentsActivity extends AppCompatActivity {

    public static ArrayList<Incident> incidentArrayList;
    public static boolean resume = false;

    IncidentPostService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidents);

        ListView listView = (ListView) findViewById(R.id.myListView);
        IncidentGetService incidentIncidentGetService = new IncidentGetService(this,listView); //getWindow().getDecorView().getRootView()
        incidentIncidentGetService.execute();

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
