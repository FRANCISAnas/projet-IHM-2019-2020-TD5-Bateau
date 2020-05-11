package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ihmprojet_2019_2020_td5_bateaux.Dialog.DeleteDialog;
import com.example.ihmprojet_2019_2020_td5_bateaux.Dialog.DetailsDialog;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.IncidentListAdapter;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentGetService;

import java.util.ArrayList;

public class IncidentsFragment extends Fragment {

    public static ArrayList<Incident> incidentArrayList;
    public static IncidentListAdapter incidentListAdapter;
    public static boolean newIncident = false;

    public static ListView listView;

    public IncidentsFragment() {
    }

    public static boolean newIncident(int id) {
        for (Incident in : incidentArrayList) {
            if (id == in.getId())
                return false;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstaceState) {
        final View rootView = inflater.inflate(R.layout.activity_incidents, container, false);
        setHasOptionsMenu(true);



        IncidentGetService incidentGetService = new  IncidentGetService(rootView);
        incidentGetService.execute();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listView = rootView.findViewById(R.id.myListView);
        incidentListAdapter = new IncidentListAdapter(getContext(), R.layout.custom_list_view, incidentArrayList);
        listView.setAdapter(incidentListAdapter);



        registerForContextMenu(listView);


        Button addIncident = rootView.findViewById(R.id.addIncident);
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
                DetailsDialog detailsDialog = new DetailsDialog((Incident) listView.getItemAtPosition(position));
                detailsDialog.show(getFragmentManager(), "gg");
            }
        });


        return rootView;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu_list_item, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.edit) {
            if (incidentArrayList.get(adapterContextMenuInfo.position).getAndroid_id().equals(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID))) {
            Bundle bundle = new Bundle();
            bundle.putString("nature", incidentArrayList.get(adapterContextMenuInfo.position).getNature());
            bundle.putString("description", incidentArrayList.get(adapterContextMenuInfo.position).getDescription());
            bundle.putInt("id", incidentArrayList.get(adapterContextMenuInfo.position).getId());
            FragmentTransaction frag = getFragmentManager().beginTransaction();
            Fragment secondFragment = new PostFragment();
            secondFragment.setArguments(bundle);
            frag.replace(R.id.fragment_container, secondFragment);
            frag.commit();
            }else{
            Toast.makeText(getContext(), "You are not allowed to edit an incident you didn't publish.", Toast.LENGTH_SHORT).show();
        }


        }

        if (item.getItemId() == R.id.delete) {

            if (incidentArrayList.get(adapterContextMenuInfo.position).getAndroid_id().equals(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID))) {
                DeleteDialog deleteDialog = new DeleteDialog(incidentArrayList.get(adapterContextMenuInfo.position));
                deleteDialog.show(getFragmentManager(), "gg");
            } else {
                Toast.makeText(getContext(), "You are not allowed to delete an incident you didn't publish.", Toast.LENGTH_SHORT).show();
            }


        }


        return super.onContextItemSelected(item);
    }



}
