package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.location.Location;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecastGetService extends AsyncTask<Void, Void, Void> {

    private double longitude;
    private double latitude;

    public WeatherForecastGetService(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String data = "";
        try {
            URL url = new URL("api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=edd48555b608285b51b1abb1d4cc1a8e");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data += line;
            }
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject;
            int i = 0;
            while (i < jsonArray.length()) {
                try {
                    jsonObject = jsonArray.getJSONObject(i);

                    String city_name = jsonObject.getString("name");
                    String visibility = jsonObject.getString("visibility");

                    JSONObject main = jsonObject.getJSONObject("main");
                    String temp = main.getString("temp");
                    String feels_like = main.getString("feels_like");
                    String temp_min = main.getString("temp_min");
                    String temp_max = main.getString("temp_max");
                    String pressure = main.getString("pressure");
                    String humidity = main.getString("humidity");

                    JSONArray weather = jsonObject.getJSONArray("weather");
                    if(weather.length() > 0){
                        JSONObject element = (JSONObject) weather.get(0);
                        String description = element.getString("description");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setLocation(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }
}
