package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.IncidentsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;

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

import static com.example.ihmprojet_2019_2020_td5_bateaux.NeptuneNotification.CHANNEL_URGENTE;

public class MyAsyncTaskWorker extends Worker {


    public final static String TAG = "FRANCIS";
    ArrayList<Incident> incidentArrayList;
    private Context mContext;
    private boolean newIncident = false;


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
                int id = jsonObject.getInt("id");
                String nature = jsonObject.getString("nature");
                String description = jsonObject.getString("description");
                String date = jsonObject.getString("date");
                String longitude = jsonObject.getString("longitude");
                String latitude = jsonObject.getString("latitude");
                String android_id = jsonObject.getString("android_id");

                Incident incident = new Incident(id, nature, description, date, longitude, latitude, android_id);
                incidentArrayList.add(incident);

                if (IncidentsFragment.incidentArrayList != null && IncidentsFragment.newIncident(incident.getId())) {
                    if (!incident.getAndroid_id().equals("null") && !incident.getAndroid_id().equals(Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID)))
                        IncidentsFragment.newIncident = true;
                    sendOnUrgent(incident);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        IncidentsFragment.incidentArrayList = incidentArrayList;


        return Result.success();

    }

    public void sendOnUrgent(Incident incident) { //View v
        if (IncidentGetService.nbOfNotification == IncidentGetService.MAX_NUMBER_OF_NOTIFICATIONS)
            IncidentGetService.nbOfNotification = 0;
        /*Intent intent = new Intent(getApplicationContext(), IncidentsFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);*/
        // final String desccription = ((EditText) getView().findViewById(R.id.editTextDescription)).getText().toString();
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(ns);
        Notification notification = new NotificationCompat.Builder(mContext, CHANNEL_URGENTE)
                .setSmallIcon(R.drawable.ic_alert)
                .setContentText(incident.getNature())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        mNotificationManager.notify(IncidentGetService.nbOfNotification++, notification);
    }


}
