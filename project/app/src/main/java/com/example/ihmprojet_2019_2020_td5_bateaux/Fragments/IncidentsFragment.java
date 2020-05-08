package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ihmprojet_2019_2020_td5_bateaux.Dialog.DeleteDialog;
import com.example.ihmprojet_2019_2020_td5_bateaux.Dialog.DetailsDialog;
import com.example.ihmprojet_2019_2020_td5_bateaux.MainActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentGetService;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentPostService;

import java.util.ArrayList;

public class IncidentsFragment extends Fragment {

    public static ArrayList<Incident> incidentArrayList;

    private IncidentPostService service;

    private ArrayAdapter adapter;

    private static final String TAG = "FRANCIS";

    public IncidentsFragment(){
        // Empty constructor required
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstaceState){
        final View rootView = inflater.inflate(R.layout.activity_incidents, container, false);
        setHasOptionsMenu(true);

       /* Button button = (Button) rootView.findViewById(R.id.go_to_Incidents_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction frag = getFragmentManager().beginTransaction();
                frag.replace(R.id.fragment_container, new HomeFragment());
                frag.commit();
            }
        });*/

        final ListView listView = (ListView) rootView.findViewById(R.id.myListView);
        registerForContextMenu(listView);
        IncidentGetService incidentGetService = new IncidentGetService(rootView.getContext(), listView); //getWindow().getDecorView().getRootView()
        incidentGetService.execute();

        Button addIncident =  rootView.findViewById(R.id.addIncident);
        addIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction frag = getFragmentManager().beginTransaction();
                frag.replace(R.id.fragment_container, new PostFragment(true));
                frag.commit();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //

                DetailsDialog detailsDialog = new DetailsDialog((Incident) listView.getItemAtPosition(position));
                detailsDialog.show(getFragmentManager(),"gg");

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

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.edit)
        {


            FragmentTransaction frag = getFragmentManager().beginTransaction();
            frag.replace(R.id.fragment_container, new PostFragment());
            frag.commit();

        }

        if(item.getItemId() == R.id.delete)
        {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            DeleteDialog deleteDialog = new DeleteDialog(incidentArrayList.get(adapterContextMenuInfo.position));
            deleteDialog.show(getFragmentManager(),"gg");

        }


        return super.onContextItemSelected(item);
    }
}
