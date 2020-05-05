package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.IncidentsFragment;
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

public class IncidentGetService extends AsyncTask<Void, Void, Void> {
    ArrayList<Incident> incidentArrayList;
    private Context mContext;
    private int progress = 0;
    private ViewGroup rootView;
    IncidentListAdapter incidentListAdapter;
    private EditText theFilter;
    ListView listView;

    public IncidentGetService(Context context, ListView listView, ViewGroup view) { //, View view)
        mContext = context;
        this.listView = listView;
        rootView = view;
    }

    @Override
    protected Void doInBackground(Void... voids) {
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
                Incident incident = new Incident(id, nature, description);
                incidentArrayList.add(incident);
              /*  if(!incidentArrayList.contains(incident)){
                    incidentArrayList.add(incident);
                    if(IncidentsActivity.resume){
                        System.out.println("incident added ! ");
                    }
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        IncidentsFragment.incidentArrayList = incidentArrayList;
        progress++;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
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



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        //ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        ///progressBar.setMax(10);
        //progressBar.setProgress(progress,true);
    }
}