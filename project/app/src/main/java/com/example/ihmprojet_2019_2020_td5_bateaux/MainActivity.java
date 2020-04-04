package com.example.ihmprojet_2019_2020_td5_bateaux;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.example.ihmprojet_2019_2020_td5_bateaux.Service.Service;

import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.AboutUsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.DirectionsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.HomeFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.IncidentsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import static com.example.ihmprojet_2019_2020_td5_bateaux.NeptuneNotification.CHANNEL_CLASSIQUE;
import static com.example.ihmprojet_2019_2020_td5_bateaux.NeptuneNotification.CHANNEL_URGENTE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static int nbOfNotification = 0;

    private NotificationManagerCompat notificationManager;
    private EditText editText;
    private DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setNotficationManagerCompat();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        // setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }

    private void setNotficationManagerCompat() {
        notificationManager = NotificationManagerCompat.from(this);
        editText = findViewById(R.id.incident_description);
    }

    public void sendOnUrgent(View v){
        if(nbOfNotification == 3)nbOfNotification = 0;
        // String description = editText.getText().toString();
        if(editText == null) System.out.print("hello World!");
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_URGENTE)
                .setSmallIcon(R.drawable.ic_alert)
                .setPriority(NotificationCompat.PRIORITY_HIGH).build();
        notificationManager.notify(nbOfNotification++, notification);

    }

    public void sendOnClassic(View v){
        if(nbOfNotification == 3)nbOfNotification = 0;
        // String description = editText.getText().toString();
        if(editText == null) System.out.print("hello World!");
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_CLASSIQUE)
                .setSmallIcon(R.drawable.ic_alert)
                .setPriority(NotificationCompat.PRIORITY_LOW).build();
        notificationManager.notify(nbOfNotification++, notification);
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.item1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.item2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DirectionsFragment()).commit();
                break;
            case R.id.item3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IncidentsFragment()).commit();
                break;
            case R.id.item4:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
            case R.id.item5:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).commit();
                break;

        }

        return true;
    }
}
