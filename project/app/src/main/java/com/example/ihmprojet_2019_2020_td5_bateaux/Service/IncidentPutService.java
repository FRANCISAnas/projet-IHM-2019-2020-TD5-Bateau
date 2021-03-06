package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class IncidentPutService extends AsyncTask<String, String, String> {
    String nature;
    int id;
    String description;
    private Context mContext;

    public IncidentPutService(Context context, String nature, String description, int id) {
        mContext = context;
        this.id = id;
        this.nature = nature;
        this.description = description;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        // create a Toast
        Toast.makeText(mContext, "Incident edited successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {

            URL url = new URL("http://www.neptune.dinelhost.com/api/incident.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoInput(true);
            conn.setDoOutput(true);


           /* Map<String, String> params = new HashMap<>();
            params.put("nature", this.nature);
            params.put("description", this.description);
*/
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", this.id);
            jsonObject.put("nature", this.nature);
            jsonObject.put("description", this.description);

  /*          StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> pa : params.entrySet()) {
                postData.append("&");
                postData.append(URLEncoder.encode(pa.getKey(), "UTF-8"));
                postData.append("=");
                postData.append(URLEncoder.encode(pa.getValue(), "UTF-8"));
            }


 */
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
