package com.example.ihmprojet_2019_2020_td5_bateaux;

import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.AboutUsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.DirectionsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.HomeFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.IncidentsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import static com.example.ihmprojet_2019_2020_td5_bateaux.NeptuneNotification.CHANNEL_CLASSIQUE;
import static com.example.ihmprojet_2019_2020_td5_bateaux.NeptuneNotification.CHANNEL_URGENTE;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int MAX_NUMBER_OF_NOTIFICATIONS = 3;
    public static int nbOfNotification = 0;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private NotificationManagerCompat notificationManager;
    private DrawerLayout drawer;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.fragment_incidents);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment mFragment = null;
        mFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mFragment).commit();

    }





    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
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
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, new HomeFragment()).commit();
                break;
        }
        return true;
    }
}
