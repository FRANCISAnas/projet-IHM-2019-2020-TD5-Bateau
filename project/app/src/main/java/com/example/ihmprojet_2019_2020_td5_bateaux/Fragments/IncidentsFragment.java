package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ihmprojet_2019_2020_td5_bateaux.IncidentsActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.MainActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentPostService;

import java.util.ArrayList;

public class IncidentsFragment extends Fragment {

    public static ArrayList<Incident> incidentArrayList;
    public static boolean resume = false;
    View root;
    IncidentPostService service;

    public IncidentsFragment(){
        // Empty constructor required
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
        View rootView = inflater.inflate(R.layout.activity_incidents, container, false);
        setHasOptionsMenu(true);

        root = rootView;
       /* Button button = (Button) rootView.findViewById(R.id.go_to_Incidents_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction frag = getFragmentManager().beginTransaction();
                frag.replace(R.id.fragment_container, new HomeFragment());
                frag.commit();
            }
        });*/

        ListView listView = (ListView) rootView.findViewById(R.id.myListView);
        registerForContextMenu(listView);
        IncidentGetService incidentGetService = new IncidentGetService(rootView.getContext(), listView); //getWindow().getDecorView().getRootView()
        incidentGetService.execute();

        Button addIncident =  rootView.findViewById(R.id.addIncident);
        addIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction frag = getFragmentManager().beginTransaction();
                frag.replace(R.id.fragment_container, new PostFragment());
                frag.commit();

            }
        });





        return rootView;
    }


    private void incidentActivityOpen() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu_list_item,menu);
    }



}
