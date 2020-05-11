package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class IncidentDeleteService extends AsyncTask<String, String, String> {


    Incident incident;
    View view;

    private Context mContext;

    public IncidentDeleteService(View view, Incident incident) {
        this.view = view;
        this.incident = incident;
    }


    @Override
    protected String doInBackground(String... strings) {
        try {

            URL url = new URL("http://www.neptune.dinelhost.com/api/incident.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoInput(true);
            conn.setDoOutput(true);





            //

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",this.incident.getId());



            byte[] paramBytes = jsonObject.toString().getBytes("UTF-8");

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());

            os.write(paramBytes);

            os.flush();
            os.close();


            int responseCode = conn.getResponseCode();
            conn.disconnect();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("success");
                return "success";

            } else
                return "";

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        IncidentGetService incidentGetService = new IncidentGetService(this.view);
        incidentGetService.execute();
    }
}
