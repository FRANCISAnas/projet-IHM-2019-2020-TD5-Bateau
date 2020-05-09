package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.core.app.NotificationCompat;

import com.example.ihmprojet_2019_2020_td5_bateaux.Data;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.IncidentsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.SettingsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.IncidentListAdapter;
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

import static com.example.ihmprojet_2019_2020_td5_bateaux.NeptuneNotification.CHANNEL_CLASSIQUE;
import static com.example.ihmprojet_2019_2020_td5_bateaux.NeptuneNotification.CHANNEL_URGENTE;

public class IncidentGetService extends AsyncTask<Void, Void, Void> {
    public static final int MAX_NUMBER_OF_NOTIFICATIONS = 3;
    public static boolean RUNNING = false;
    public static int nbOfNotification = 0;
    ArrayList<Incident> incidentArrayList;
    boolean newIncident = false;
    IncidentListAdapter incidentListAdapter;
    private Context mContext;
    private ListView listView;
    private View rootView;
    private EditText theFilter;


    public IncidentGetService(Context context, ListView listView, View view) { //, )
        mContext = context;
        this.listView = listView;
        rootView = view;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        RUNNING = true;
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

                Incident incident = new Incident(id, nature, description, date, longitude, latitude, android_id);
                incidentArrayList.add(incident);

                if (IncidentsFragment.incidentArrayList != null && IncidentsFragment.newIncident(incident.getId())) {
                    if (!incident.getAndroid_id().equals("null") && !incident.getAndroid_id().equals(Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID)))
                        sendNotification(incident, this.mContext);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        IncidentsFragment.incidentArrayList = incidentArrayList;
        return null;
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        RUNNING = false;
        super.onPostExecute(aVoid);
        theFilter = rootView.findViewById(R.id.filter_incident);

        incidentListAdapter = new IncidentListAdapter(mContext, R.layout.custom_list_view, incidentArrayList);
        listView.setAdapter(incidentListAdapter);

        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (IncidentGetService.this).incidentListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                incidentListAdapter.getFilter().filter(s.toString());
            }
        });


    }


    public static void sendOnUrgent(Incident incident, Context mContext) { //View v
        if (nbOfNotification == MAX_NUMBER_OF_NOTIFICATIONS)
            nbOfNotification = 0;
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(ns);
        Notification notification = new NotificationCompat.Builder(mContext, CHANNEL_URGENTE)
                .setSmallIcon(R.drawable.ic_alert)
                .setContentText(incident.getNature())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        mNotificationManager.notify(nbOfNotification++, notification);
    }

    public static void sendNotification(Incident incident, Context mContext) {
        if(SettingsFragment.URGENT_NOTIFICATIONS){
            sendOnUrgent(incident, mContext);
        }
        else{
            sendOnclassic(incident, mContext);
        }
    }

    public static void sendOnclassic(Incident incident, Context mContext) {
        if (nbOfNotification == MAX_NUMBER_OF_NOTIFICATIONS)
            nbOfNotification = 0;
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(ns);
        Notification notification = new NotificationCompat.Builder(mContext, CHANNEL_CLASSIQUE)
                .setSmallIcon(R.drawable.ic_alert)
                .setContentText(incident.getNature())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        mNotificationManager.notify(nbOfNotification++, notification);
    }
}
