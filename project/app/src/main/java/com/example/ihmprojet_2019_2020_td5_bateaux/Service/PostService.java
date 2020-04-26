package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PostService extends AsyncTask<String,String, String> {

    String incidentToSend ="{ \"fromPostExexute\", \"fromPostExexute test\" }";
    private Context mContext;

    public PostService(Context context){
        mContext=context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        // create a Toast
        Toast.makeText(mContext,"Incident Submited successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try{

            URL url = new URL("http://www.neptune.dinelhost.com/api/incident.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //conn.setRequestProperty("Accept","application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            /*JSONObject jsonObject = new JSONObject();
            jsonObject.put("nature","nature from post service");
            jsonObject.put("description","nature from post service");*/

            Map<String,String> params = new HashMap<>();
            params.put("nature","test de post");
            params.put("description","this is a test from post service");

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,String> pa : params.entrySet()) {
                postData.append("&");
                postData.append(URLEncoder.encode(pa.getKey(),"UTF-8"));
                postData.append("=");
                postData.append(URLEncoder.encode(pa.getValue(),"UTF-8"));
            }

            byte[] paramBytes = postData.toString().getBytes("UTF-8");

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());

            //os.writeBytes(URLEncoder.encode(postData.toString(), "UTF-8"));
            os.write(paramBytes);

            os.flush();
            os.close();

            int responseCode=conn.getResponseCode();
            conn.disconnect();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("success");
                return "success";

            }
            else
                return "";
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return null;
    }
}
