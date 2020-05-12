package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Base64;
import android.widget.Toast;

import com.example.ihmprojet_2019_2020_td5_bateaux.MainActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class IncidentPostService extends AsyncTask<String, String, String> {

    String nature;
    String description;
    Bitmap photo;
    private Context mContext;

    public IncidentPostService(Context context, String nature, String description, Bitmap photo) {
        mContext = context;
        this.nature = nature;
        this.description = description;
        this.photo = photo;
    }

    public IncidentPostService(Context context, String nature, String description) {
        mContext = context;
        this.nature = nature;
        this.description = description;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // create a Toast
        Toast.makeText(mContext, "Incident Submited successfully", Toast.LENGTH_SHORT).show();
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

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nature", this.nature);
            jsonObject.put("description", this.description);
            if (photo != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                jsonObject.put("image", encodedImage);
            } else {
                jsonObject.put("image", "null");
            }
            jsonObject.put("longitude", "" + MainActivity.currentLocation.getLongitude());
            jsonObject.put("latitude", "" + MainActivity.currentLocation.getLatitude());
            jsonObject.put("android_id", Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));

               /*Map<String, String> params = new HashMap<>();
               params.put("nature", this.nature);
               params.put("description", this.description);
               if(photo!=null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    params.put("image", encodedImage);
                }else{
                    params.put("image", "null");
                }

                params.put("longitude", "" + MainActivity.currentLocation.getLongitude());
                params.put("latitude", "" + MainActivity.currentLocation.getLatitude());
                params.put("android_id", Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));



                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, String> pa : params.entrySet()) {
                    postData.append("&");
                    postData.append(URLEncoder.encode(pa.getKey(), "UTF-8"));
                    postData.append("=");
                    postData.append(URLEncoder.encode(pa.getValue(), "UTF-8"));
                }*/

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
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
