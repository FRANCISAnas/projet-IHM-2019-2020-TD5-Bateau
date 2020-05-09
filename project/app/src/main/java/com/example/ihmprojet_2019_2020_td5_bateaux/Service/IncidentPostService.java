package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class IncidentPostService extends AsyncTask<String,String, String> {

    String nature;
    String description;
    private Context mContext;

    public IncidentPostService(Context context, String nature, String description){
        mContext=context;
        this.nature = nature;
        this.description = description;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        // create a Toast
        Toast.makeText(mContext,"Incident Submited successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... strings) {
            try {

                URL url = new URL("http://www.neptune.dinelhost.com/api/incident.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                Map<String, String> params = new HashMap<>();
                params.put("nature", this.nature);
                params.put("description", this.description);
                //params.put("longitude",);
                //params.put("latitude", );
                params.put("android_id", Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));
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
            } catch (Exception e) {
                e.printStackTrace();
            }


        return null;
    }
}
