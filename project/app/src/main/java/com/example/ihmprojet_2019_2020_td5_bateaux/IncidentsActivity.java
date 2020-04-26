package com.example.ihmprojet_2019_2020_td5_bateaux;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.IncidentListAdapter;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.PostService;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.Service;

import java.util.ArrayList;

public class IncidentsActivity extends AppCompatActivity {

    public static ArrayList<Incident> incidentArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidents);

        ListView listView = (ListView) findViewById(R.id.myListView);
        Service incidentService = new Service(this,listView); //getWindow().getDecorView().getRootView()
        incidentService.execute();

        PostService service = new PostService(this);
        service.execute();



    }
}
