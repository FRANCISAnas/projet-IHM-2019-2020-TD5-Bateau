package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentGetService;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.MyAsyncTaskWorker;

import java.util.concurrent.TimeUnit;


public class HomeFragment extends Fragment {

    public static final String TAG_MY_WORK = "mywork";

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        /*
         *Cette partie du code est chargée de récupérer les données toutes les 15 minutes (Contrainte du Work Manager)
         *Nous avons donc le IncidentGetService qui récupèrent les données quand l'utilisateur le demande et MyAsyncTaskWorker qui est lancé périodiquement.
         * Nous avons ajouté la variable statique RUNNING pour éviter que les deux tournent en même temps.
         */



       /* if (!IncidentGetService.RUNNING) {
            PeriodicWorkRequest.Builder incident =
                    new PeriodicWorkRequest.Builder(MyAsyncTaskWorker.class, 5, TimeUnit.SECONDS);
            PeriodicWorkRequest request = incident
                    .build();
            WorkManager.getInstance().enqueueUniquePeriodicWork(TAG_MY_WORK, ExistingPeriodicWorkPolicy.KEEP, request);
            //WorkManager.getInstance().enqueue(request);

            WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId()).observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                @Override
                public void onChanged(WorkInfo workInfo) {

                    if (workInfo.getState().equals(WorkInfo.State.ENQUEUED) && IncidentsFragment.incidentArrayList != null) {

                    }
                }
            });
        }*/

        IncidentGetService incidentGetService = new  IncidentGetService(rootView);
        incidentGetService.execute();


        return rootView;
    }

}
