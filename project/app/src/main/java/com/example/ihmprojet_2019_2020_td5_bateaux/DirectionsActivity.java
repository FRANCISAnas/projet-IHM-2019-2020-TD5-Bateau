package com.example.ihmprojet_2019_2020_td5_bateaux;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.IncidentsFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentGetService;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import static java.sql.Types.FLOAT;

public class DirectionsActivity extends AppCompatActivity {
    private static String TAG = "OSM_ACTIVITY";
    private MapView map;
    private IncidentsFragment incidentsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_directions);
        Log.d(TAG, "onCreat() start");
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        double J = MainActivity.currentLocation.getLatitude();
        double R = MainActivity.currentLocation.getLongitude();
        GeoPoint startPoint = new GeoPoint(J,R);
        IMapController mapController=map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        ArrayList<OverlayItem> items = new ArrayList<>();


        OverlayItem home = new OverlayItem(" you are here ","depart", new GeoPoint(J, R));
        //forme marqueur
        Drawable m = home.getMarker(0);
        items.add(home);
        for (Incident in : IncidentsFragment.incidentArrayList){
            items.add(new OverlayItem(in.getNature(), " ", new GeoPoint(Float.parseFloat(in.getLatitude()),Float.parseFloat(in.getLongitude()))));

        }
        // ArrayList<Incident> list = IncidentsFragment.incidentArrayList;
        //Log.d(TAG, "LONGITUDE");
        //double L= Float.parseFloat(list.get(0).getLatitude());
        //double K= Float.parseFloat(list.get(0).getLongitude());

        //items.add(new OverlayItem(list.get(0).getNature(),"", new GeoPoint(L,K)));
        ItemizedOverlayWithFocus<OverlayItem> mOverLay= new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(), items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

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
        map.onResume();
    }
}
