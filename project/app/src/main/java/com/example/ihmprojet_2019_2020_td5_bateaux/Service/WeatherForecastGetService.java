package com.example.ihmprojet_2019_2020_td5_bateaux.Service;

import android.location.Location;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecastGetService extends AsyncTask<Void, Void, Void> {

    private double longitude;
    private double latitude;

    public WeatherForecastGetService(Location location){
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String data = "";
        try {
            URL url = new URL("api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=edd48555b608285b51b1abb1d4cc1a8e");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
