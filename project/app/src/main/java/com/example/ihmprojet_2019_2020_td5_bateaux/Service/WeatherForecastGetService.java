package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.location.Location;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeatherForecastGetService extends AsyncTask<Void, Void, Void> {

    private Location location;
    private HashMap<String, TextView> textViews;
    private HashMap<String, String> values;

    private static final String CITY_NAME = "name";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String VISIBILITY = "visibility";
    private static final String TIMEZONE = "timezone";
    private static final String DESCRIPTION = "description";
    private static final String SUNRISE = "sunrise";
    private static final String SUNSET = "sunset";
    private static final String TEMPERATURE = "temp";
    private static final String FEELS_LIKE = "feels_like";
    private static final String TEMPERATURE_MIN = "temp_min";
    private static final String TEMPERATURE_MAX = "temp_max";
    private static final String PRESSURE = "pressure";
    private static final String HUMIDITY = "humidity";
    private static final String TIME_FORMAT = "HH:mm:ss";


    public WeatherForecastGetService(Location location, View view) {
        this.location = location;
        values = new HashMap<>();
        values.put(LATITUDE, "" + location.getLatitude());
        values.put(LONGITUDE, "" + location.getLongitude());
        textViews = new HashMap<>();
        textViews.put(CITY_NAME, (TextView) view.findViewById(R.id.city_name));
        textViews.put(LATITUDE, (TextView) view.findViewById(R.id.latitude));
        textViews.put(LONGITUDE, (TextView) view.findViewById(R.id.longitude));
        textViews.put(VISIBILITY, (TextView) view.findViewById(R.id.visibility));
        textViews.put(TIMEZONE, (TextView) view.findViewById(R.id.timezone));
        textViews.put(DESCRIPTION, (TextView) view.findViewById(R.id.description));
        textViews.put(SUNRISE, (TextView) view.findViewById(R.id.sunrise));
        textViews.put(SUNSET, (TextView) view.findViewById(R.id.sunset));
        textViews.put(TEMPERATURE, (TextView) view.findViewById(R.id.temp));
        textViews.put(FEELS_LIKE, (TextView) view.findViewById(R.id.feels_like));
        textViews.put(TEMPERATURE_MIN, (TextView) view.findViewById(R.id.temp_min));
        textViews.put(TEMPERATURE_MAX, (TextView) view.findViewById(R.id.temp_max));
        textViews.put(PRESSURE, (TextView) view.findViewById(R.id.pressure));
        textViews.put(HUMIDITY, (TextView) view.findViewById(R.id.humidity));
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


            if (jsonObject.has(CITY_NAME)) {
                values.put(CITY_NAME, jsonObject.getString(CITY_NAME));
            }
            if (jsonObject.has(VISIBILITY)) {
                values.put(VISIBILITY, jsonObject.getString(VISIBILITY) + " m");
            }
            if (jsonObject.has(TIMEZONE)) {
                values.put(TIMEZONE, "GMT " + jsonObject.getInt(TIMEZONE) / 3600);
            }

            if (jsonObject.has("weather")) {
                JSONArray weather = jsonObject.getJSONArray("weather");
                if (weather.length() > 0) {
                    JSONObject element = (JSONObject) weather.get(0);
                    if (element.has(DESCRIPTION)) {
                        values.put(DESCRIPTION, element.getString(DESCRIPTION));
                    }
                }
            }

            if (jsonObject.has("sys")) {
                JSONObject sys = jsonObject.getJSONObject("sys");
                if (sys.has(SUNRISE)) {
                    values.put(SUNRISE, new SimpleDateFormat(TIME_FORMAT).format(new Date(sys.getInt(SUNRISE))));
                }
                if (sys.has(SUNSET)) {
                    values.put(SUNSET, new SimpleDateFormat(TIME_FORMAT).format(new Date(sys.getInt(SUNSET))));
                }
            }

            if (jsonObject.has("main")) {
                JSONObject main = jsonObject.getJSONObject("main");
                if (main.has(TEMPERATURE)) {
                    values.put(TEMPERATURE, main.getString(TEMPERATURE) + " °F");
                }
                if (main.has(FEELS_LIKE)) {
                    values.put(FEELS_LIKE, main.getString(FEELS_LIKE) + " °F");
                }
                if (main.has(TEMPERATURE_MIN)) {
                    values.put(TEMPERATURE_MIN, main.getString(TEMPERATURE_MIN) + " °F");
                }
                if (main.has(TEMPERATURE_MAX)) {
                    values.put(TEMPERATURE_MAX, main.getString(TEMPERATURE_MAX) + " °F");
                }
                if (main.has(PRESSURE)) {
                    values.put(PRESSURE, main.getString(PRESSURE) + " hPa");
                }
                if (main.has(HUMIDITY)) {
                    values.put(HUMIDITY, main.getString(HUMIDITY) + "%");
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
        for (Map.Entry<String, String> e : values.entrySet()) {
            textViews.get(e.getKey()).setText(e.getValue());
        }
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
