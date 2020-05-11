package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.location.Location;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class WeatherForecastGetService extends AsyncTask<Void, Void, Void> {

    private Location location;
    private HashMap<String, TextView> textViews;
    private HashMap<String, String> values;


    public WeatherForecastGetService(Location location, HashMap<String, TextView> textViews) {
        this.location = location;
        this.textViews = textViews;
        values.put(HomeFragment.LATITUDE, "" + location.getLatitude());
        values.put(HomeFragment.LONGITUDE, "" + location.getLongitude());
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


            if (jsonObject.has(HomeFragment.CITY_NAME)) {
                values.put(HomeFragment.CITY_NAME, jsonObject.getString(HomeFragment.CITY_NAME));
            }
            if (jsonObject.has(HomeFragment.VISIBILITY)) {
                values.put(HomeFragment.VISIBILITY, jsonObject.getString(HomeFragment.VISIBILITY));
            }
            if (jsonObject.has(HomeFragment.TIMEZONE)) {
                values.put(HomeFragment.TIMEZONE, jsonObject.getString(HomeFragment.TIMEZONE));
            }

            if (jsonObject.has("weather")) {
                JSONArray weather = jsonObject.getJSONArray("weather");
                if (weather.length() > 0) {
                    JSONObject element = (JSONObject) weather.get(0);
                    if (element.has(HomeFragment.DESCRIPTION)) {
                        values.put(HomeFragment.DESCRIPTION, element.getString(HomeFragment.DESCRIPTION));
                    }
                }
            }

            if (jsonObject.has("sys")) {
                JSONObject sys = jsonObject.getJSONObject("sys");
                if (sys.has(HomeFragment.SUNRISE)) {
                    values.put(HomeFragment.SUNRISE, sys.getString(HomeFragment.SUNRISE));
                }
                if (sys.has(HomeFragment.SUNSET)) {
                    values.put(HomeFragment.SUNSET, sys.getString(HomeFragment.SUNSET));
                }
            }

            if (jsonObject.has("main")) {
                JSONObject main = jsonObject.getJSONObject("main");
                if (main.has(HomeFragment.TEMPERATURE)) {
                    values.put(HomeFragment.TEMPERATURE, main.getString(HomeFragment.TEMPERATURE));
                }
                if (main.has(HomeFragment.FEELS_LIKE)) {
                    values.put(HomeFragment.FEELS_LIKE, main.getString(HomeFragment.FEELS_LIKE));
                }
                if (main.has(HomeFragment.TEMPERATURE_MIN)) {
                    values.put(HomeFragment.TEMPERATURE_MIN, main.getString(HomeFragment.TEMPERATURE_MIN));
                }
                if (main.has(HomeFragment.TEMPERATURE_MAX)) {
                    values.put(HomeFragment.TEMPERATURE_MAX, main.getString(HomeFragment.TEMPERATURE_MAX));
                }
                if (main.has(HomeFragment.PRESSURE)) {
                    values.put(HomeFragment.PRESSURE, main.getString(HomeFragment.PRESSURE));
                }
                if (main.has(HomeFragment.HUMIDITY)) {
                    values.put(HomeFragment.HUMIDITY, main.getString(HomeFragment.HUMIDITY));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
