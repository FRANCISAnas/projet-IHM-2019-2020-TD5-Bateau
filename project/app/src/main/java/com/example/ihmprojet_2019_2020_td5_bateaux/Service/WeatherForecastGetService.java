package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.location.Location;
import android.os.AsyncTask;
import android.view.View;

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

    private Location location;
    private View view;
    private String  city_name;
    private String  visibility;
    private String  timezone;
    private String  description;
    private String  sunrise;
    private String  sunset;
    private String  temp;
    private String  feels_like;
    private String  temp_min;
    private String  temp_max;
    private String  pressure;
    private String  humidity;

    public WeatherForecastGetService(Location location, View view) {
        this.location = location;
        this.view = view;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String data = "";
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=edd48555b608285b51b1abb1d4cc1a8e");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data += line;
            }

            JSONObject jsonObject = new JSONObject(data);


            if (jsonObject.has("name")) {
                city_name = jsonObject.getString("name");
            }
            if (jsonObject.has("visibility")) {
                visibility = jsonObject.getString("visibility");
            }
            if (jsonObject.has("timezone")) {
                timezone = jsonObject.getString("timezone");
            }

            if (jsonObject.has("weather")) {
                JSONArray weather = jsonObject.getJSONArray("weather");
                if (weather.length() > 0) {
                    JSONObject element = (JSONObject) weather.get(0);
                    if (element.has("description")) {
                        description = element.getString("description");
                    }
                }
            }

            if (jsonObject.has("sys")) {
                JSONObject sys = jsonObject.getJSONObject("sys");
                if (sys.has("sunrise")) {
                    sunrise = sys.getString("sunrise");
                }
                if (sys.has("sunset")) {
                    sunset = sys.getString("sunset");
                }
            }

            if (jsonObject.has("main")) {
                JSONObject main = jsonObject.getJSONObject("main");
                if (main.has("temp")) {
                    temp = main.getString("temp");
                }
                if (main.has("feels_like")) {
                    feels_like = main.getString("feels_like");
                }
                if (main.has("temp_min")) {
                    temp_min = main.getString("temp_min");
                }
                if (main.has("temp_max")) {
                    temp_max = main.getString("temp_max");
                }
                if (main.has("pressure")) {
                    pressure = main.getString("pressure");
                }
                if (main.has("humidity")) {
                    humidity = main.getString("humidity");
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
