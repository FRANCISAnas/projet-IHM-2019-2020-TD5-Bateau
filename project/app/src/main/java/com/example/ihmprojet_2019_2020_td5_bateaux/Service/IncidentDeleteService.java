package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;

import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class IncidentDeleteService extends AsyncTask<String,String,String> {


    Incident incident;

    private Context mContext;

    public IncidentDeleteService(Context context, Incident incident){
        mContext=context;
        this.incident = incident;
    }


    @Override
    protected String doInBackground(String... strings) {
        try {

            //URL url = new URL(String.format("%s/&id=%d","http://www.neptune.dinelhost.com/api/incident.php",incident.getId()));
            URL url = new URL("http://www.neptune.dinelhost.com/api/incident.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoInput(true);
            conn.setDoOutput(true);



            Map<String, String> params = new HashMap<>();
            params.put("nature", Integer.toString(this.incident.getId()));

            //

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> pa : params.entrySet()) {
                postData.append("&");
                postData.append(URLEncoder.encode(pa.getKey(), "UTF-8"));
                postData.append("=");
                postData.append(URLEncoder.encode(pa.getValue(), "UTF-8"));
            }

            byte[] paramBytes = postData.toString().getBytes("UTF-8");

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

        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;
    }
}
