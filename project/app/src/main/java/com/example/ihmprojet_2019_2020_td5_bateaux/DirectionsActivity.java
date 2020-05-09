package com.example.ihmprojet_2019_2020_td5_bateaux;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

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
    private static String TAG = "OSM_ACTIVITY";
    private MapView map;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_directions);
        Log.d(TAG,"onCreat() start");
        map=findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        GeoPoint startPoint = new GeoPoint(32.3213840,-64.75737);
        IMapController mapController=map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        ArrayList<OverlayItem> items = new ArrayList<>();
        OverlayItem home = new OverlayItem("position1","depart", new GeoPoint(32.3213844, -64.75737));
        //forme marqueur
        Drawable m = home.getMarker(0);
        items.add(home);
        items.add(new OverlayItem("position2", "arrivee", new GeoPoint(32.3213840,-64.75730)));
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
    public void onPause(){
        super.onPause();
        map.onPause();
    }
    public void onResume(){
        super.onResume();
        map.onResume();
    }
}
