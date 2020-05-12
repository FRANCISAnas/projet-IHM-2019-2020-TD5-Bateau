package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.ihmprojet_2019_2020_td5_bateaux.MainActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentGetService;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.MyAsyncTaskWorker;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.WeatherForecastGetService;

import java.util.concurrent.TimeUnit;


public class HomeFragment extends Fragment {

    View rootView;
    private WeatherForecastGetService weatherForecastGetService;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        weatherForecastGetService = new WeatherForecastGetService(MainActivity.currentLocation, rootView);
        weatherForecastGetService.execute();

        /*
         * Cette partie du code est chargée de récupérer les données toutes les 15 minutes (Contrainte du Work Manager)
         * Nous avons donc le IncidentGetService qui récupèrent les données quand l'utilisateur le demande et MyAsyncTaskWorker qui est lancé périodiquement.
         * Nous avons ajouté la variable statique RUNNING pour éviter que les deux tournent en même temps.
         */

        if (!IncidentGetService.RUNNING) {
            PeriodicWorkRequest.Builder incident =
                    new PeriodicWorkRequest.Builder(MyAsyncTaskWorker.class, 5, TimeUnit.SECONDS);
            PeriodicWorkRequest request = incident
                    .build();
            WorkManager.getInstance().enqueueUniquePeriodicWork("TAG", ExistingPeriodicWorkPolicy.KEEP, request);
            WorkManager.getInstance().enqueue(request);

            WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId()).observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                @Override
                public void onChanged(WorkInfo workInfo) {

                    if (workInfo.getState().equals(WorkInfo.State.ENQUEUED) && IncidentsFragment.incidentArrayList != null) {

                    }
                }
            });
        }

        IncidentGetService incidentGetService = new IncidentGetService(rootView);
        incidentGetService.execute();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.fusedLocationProviderClient.getLastLocation();
        // weatherForecastGetService.setLocation(MainActivity.currentLocation);
        WeatherForecastGetService ws = new WeatherForecastGetService(MainActivity.currentLocation, rootView);
        if (weatherForecastGetService.getStatus() == AsyncTask.Status.FINISHED) {
            ws.execute();
        }
        /*if (weatherForecastGetService.getStatus() == AsyncTask.Status.FINISHED) {
            weatherForecastGetService.execute();
        }*/
    }

}
