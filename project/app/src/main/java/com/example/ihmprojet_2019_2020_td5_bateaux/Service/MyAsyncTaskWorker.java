package com.example.ihmprojet_2019_2020_td5_bateaux.Service;


import android.content.Context;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.ihmprojet_2019_2020_td5_bateaux.Data;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.IncidentsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MyAsyncTaskWorker extends Worker {


    ArrayList<Incident> incidentArrayList;
    private Context mContext;


    public MyAsyncTaskWorker(@NonNull Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;

    }

    @NonNull
    @Override
    public Result doWork() {

        String data = "";
        try {


            URL url = new URL("http://www.neptune.dinelhost.com/api/incident.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject;
        incidentArrayList = new ArrayList<>();
        int i = 0;
        while (i < jsonArray.length()) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt(String.valueOf(Data.id));
                String nature = jsonObject.getString(String.valueOf(Data.nature));
                String description = jsonObject.getString(String.valueOf(Data.description));
                String date = jsonObject.getString(String.valueOf(Data.date));
                String longitude = jsonObject.getString(String.valueOf(Data.longitude));
                String latitude = jsonObject.getString(String.valueOf(Data.latitude));
                String android_id = jsonObject.getString(String.valueOf(Data.android_id));
                String image = null;
                if (!jsonObject.getString("image").equals("null")) {
                    image = jsonObject.getString("image");
                }
                Incident incident = new Incident(id, nature, description, date, longitude, latitude, android_id, image);
                incidentArrayList.add(incident);

                if (IncidentsFragment.incidentArrayList != null && IncidentsFragment.newIncident(incident.getId())) {
                    if (!incident.getAndroid_id().equals("null") && !incident.getAndroid_id().equals(Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID)))
                        IncidentsFragment.newIncident = true;
                    IncidentGetService.sendNotification(incident, this.mContext);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        IncidentsFragment.incidentArrayList = incidentArrayList;


        return Result.success();

    }


}
