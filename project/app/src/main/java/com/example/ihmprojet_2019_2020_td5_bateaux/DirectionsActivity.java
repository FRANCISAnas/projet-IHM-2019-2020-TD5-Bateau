package com.example.ihmprojet_2019_2020_td5_bateaux;

import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.IncidentsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class DirectionsActivity extends AppCompatActivity {
    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_directions);
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);

        Location location = MainActivity.currentLocation;
        GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        ArrayList<OverlayItem> items = new ArrayList<>();
        OverlayItem home = new OverlayItem("You are here", "Departure point", startPoint);

        items.add(home);
        for (Incident in : IncidentsFragment.incidentArrayList) {
            items.add(new OverlayItem(in.getNature(), "A snippet", new GeoPoint(Float.parseFloat(in.getLatitude()), Float.parseFloat(in.getLongitude()))));

        }

        ItemizedOverlayWithFocus<OverlayItem> mOverLay = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(), items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });

        mOverLay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverLay);
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    public void onResume() {
        super.onResume();
        MainActivity.fusedLocationProviderClient.getLastLocation();
        map.onResume();
    }

}
