package com.example.ihmprojet_2019_2020_td5_bateaux;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


public class NeptuneNotification extends Application {
    public static final String CHANNEL_URGENTE = "Urgence";
    public static final String CHANNEL_CLASSIQUE = "Classic";



    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel urgentChannel = new NotificationChannel(CHANNEL_URGENTE, "Urgent Channel", NotificationManager.IMPORTANCE_HIGH);

            urgentChannel.setDescription("This is Urgent Channel");
            NotificationChannel classicChannel =  new NotificationChannel(CHANNEL_CLASSIQUE, "Classic channel", NotificationManager.IMPORTANCE_DEFAULT);

            classicChannel.setDescription("This is Classic Channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(classicChannel);
            manager.createNotificationChannel(urgentChannel);

        }

    }

}
