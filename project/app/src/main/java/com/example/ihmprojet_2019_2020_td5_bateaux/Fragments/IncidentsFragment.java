package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;

import com.example.ihmprojet_2019_2020_td5_bateaux.Dialog.DeleteDialog;
import com.example.ihmprojet_2019_2020_td5_bateaux.Dialog.DetailsDialog;
import com.example.ihmprojet_2019_2020_td5_bateaux.MainActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.IncidentListAdapter;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentGetService;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentPostService;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.MyAsyncTaskWorker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.ihmprojet_2019_2020_td5_bateaux.NeptuneNotification.CHANNEL_URGENTE;

public class IncidentsFragment extends Fragment {

    public static ArrayList<Incident> incidentArrayList;
    public static boolean newIncident = false;
    public static int nbOfNotification = 0;
    private static final int MAX_NUMBER_OF_NOTIFICATIONS = 3;
    public static ListView listView;

    private static final String TAG = "FRANCIS";
    public IncidentsFragment(){
        // Empty constructor required
}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstaceState){
        final View rootView = inflater.inflate(R.layout.activity_incidents, container, false);
        setHasOptionsMenu(true);

        listView =  rootView.findViewById(R.id.myListView);
        registerForContextMenu(listView);
        IncidentGetService incidentGetService = new IncidentGetService(rootView.getContext(), listView, rootView);
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
                DetailsDialog detailsDialog = new DetailsDialog((Incident) listView.getItemAtPosition(position));
                detailsDialog.show(getFragmentManager(),"gg");
            }
        });


        return rootView;
    }





    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu_list_item,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId() == R.id.edit)
        {

            Bundle bundle = new Bundle();
            bundle.putString("nature",incidentArrayList.get(adapterContextMenuInfo.position).getNature());
            bundle.putString("description",incidentArrayList.get(adapterContextMenuInfo.position).getDescription());
            FragmentTransaction frag = getFragmentManager().beginTransaction();
            Fragment secondFragment = new PostFragment();
            secondFragment.setArguments(bundle);
            frag.replace(R.id.fragment_container, secondFragment);
            frag.commit();


        }

        if(item.getItemId() == R.id.delete)
        {

            if(incidentArrayList.get(adapterContextMenuInfo.position).getAndroid_id().equals(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID))){
                DeleteDialog deleteDialog = new DeleteDialog(incidentArrayList.get(adapterContextMenuInfo.position));
                deleteDialog.show(getFragmentManager(),"gg");
            }else{
                Toast.makeText(getContext(),"Now why would you do that ehh! not cool man not cool",Toast.LENGTH_SHORT).show();
            }



        }


        return super.onContextItemSelected(item);
    }


    public static boolean newIncident(int id){
        for (Incident in : incidentArrayList) {
            if(id == in.getId())
                return false;
        }
        return true;
    }





}
