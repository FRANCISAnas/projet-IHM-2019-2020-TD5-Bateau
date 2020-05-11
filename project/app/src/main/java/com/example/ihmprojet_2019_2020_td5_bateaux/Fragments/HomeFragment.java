package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class HomeFragment extends Fragment {

    private WeatherForecastGetService weatherForecastGetService;

    private HashMap<String, TextView> textViews;

    public static final String CITY_NAME = "name";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE ="longitude";
    public static final String VISIBILITY = "visibility";
    public static final String TIMEZONE = "timezone";
    public static final String DESCRIPTION = "description";
    public static final String SUNRISE = "sunrise";
    public static final String SUNSET = "sunset";
    public static final String TEMPERATURE = "temp";
    public static final String FEELS_LIKE = "feels_like";
    public static final String TEMPERATURE_MIN = "temp_min";
    public static final String TEMPERATURE_MAX = "temp_max";
    public static final String PRESSURE = "pressure";
    public static final String HUMIDITY = "humidity";

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        textViews = new HashMap<>();
        textViews.put(CITY_NAME, (TextView) container.findViewById(R.id.city_name));
        textViews.put(LATITUDE, (TextView) container.findViewById(R.id.latitude));
        textViews.put(LONGITUDE, (TextView) container.findViewById(R.id.longitude));
        textViews.put(VISIBILITY, (TextView) container.findViewById(R.id.visibility));
        textViews.put(TIMEZONE, (TextView) container.findViewById(R.id.timezone));
        textViews.put(DESCRIPTION, (TextView) container.findViewById(R.id.description));
        textViews.put(SUNRISE, (TextView) container.findViewById(R.id.sunrise));
        textViews.put(SUNSET, (TextView) container.findViewById(R.id.sunset));
        textViews.put(TEMPERATURE, (TextView) container.findViewById(R.id.temp));
        textViews.put(FEELS_LIKE, (TextView) container.findViewById(R.id.feels_like));
        textViews.put(TEMPERATURE_MIN, (TextView) container.findViewById(R.id.temp_min));
        textViews.put(TEMPERATURE_MAX, (TextView) container.findViewById(R.id.temp_max));
        textViews.put(PRESSURE, (TextView) container.findViewById(R.id.pressure));
        textViews.put(HUMIDITY, (TextView) container.findViewById(R.id.humidity));

        weatherForecastGetService = new WeatherForecastGetService(MainActivity.currentLocation, textViews);
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
            //WorkManager.getInstance().enqueue(request);

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
        weatherForecastGetService.setLocation(MainActivity.currentLocation);
        if (weatherForecastGetService.getStatus() == AsyncTask.Status.FINISHED) {
            weatherForecastGetService.execute();
        }
    }

}
