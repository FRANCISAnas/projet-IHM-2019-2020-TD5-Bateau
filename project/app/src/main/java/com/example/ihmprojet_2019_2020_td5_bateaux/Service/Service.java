package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.HttpAuthHandler;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ihmprojet_2019_2020_td5_bateaux.IncidentsActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.MainActivity;
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

public class Service extends AsyncTask<Void,Void,Void> {
    ArrayList<Incident> incidentArrayList;
    private Context mContext;
    ListView listView;

    public Service(Context context,ListView listView){
        mContext=context;
        this.listView = listView;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String data="";



        try {
            URL url = new URL("http://www.neptune.dinelhost.com/api/incident.php");

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            JSONObject jsonObject;
            while(line!=null){
                line = bufferedReader.readLine();
                data+=line;
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

         incidentArrayList =new ArrayList<>();

        int i = 0 ;
        while(i<jsonArray.length()){
            try {
                jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String nature = jsonObject.getString("nature");
                String description = jsonObject.getString("description");

                Incident incident = new Incident(id,nature,description);
                incidentArrayList.add(incident);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        IncidentsActivity.incidentArrayList = incidentArrayList;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        IncidentListAdapter incidentListAdapter = new IncidentListAdapter(mContext,R.layout.custom_list_view,incidentArrayList);
        listView.setAdapter(incidentListAdapter);

    }


}
