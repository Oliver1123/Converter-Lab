package com.example.oliver.someexample.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.GetLocationTask;
import com.example.oliver.someexample.Listener.GetLocationListener;
import com.example.oliver.someexample.Model.OrgInfoModel;
import com.example.oliver.someexample.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GetLocationListener, View.OnClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private OrgInfoModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        model = getIntent().getParcelableExtra(Constants.ORG_INFO_MODEL_ARG);
        findViewById(R.id.ivMapHybrid).setOnClickListener(this);
        findViewById(R.id.ivMapSatellite).setOnClickListener(this);
        findViewById(R.id.ivMapTerrain).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        Log.d(Constants.TAG, "MapActivity onResume");
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMapAsync(this);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(Constants.TAG, "MapsActivity onMapReady");
        mMap = googleMap;
        if (mMap == null) return;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        findAddress();
    }

    private void findAddress() {
        StringBuilder address = new StringBuilder();

        if (!Constants.UNKNOWN_VALUE.equals(model.getRegionTitle())) {
            address.append(model.getRegionTitle());
        }
        if (!Constants.UNKNOWN_VALUE.equals(model.getRegionTitle()) &&
                !model.getRegionTitle().equals(model.getCityTitle())) {
            address.append(", ");
            address.append(model.getCityTitle());
        }
        if (!Constants.UNKNOWN_VALUE.equals(model.getAddress())) {
            address.append(", ");
            address.append(model.getAddress());
        }
        Log.d(Constants.TAG, "MapsActivity target address: " + address.toString());
        new GetLocationTask(this).execute(address.toString());
    }

    @Override
    public void success(LatLng result) {
        if (mMap != null) {

            mMap.addMarker(new MarkerOptions()
                    .title(model.getTitle())
                    .position(result));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(result)
                    .zoom(17)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.moveCamera(cameraUpdate);
        }
    }

    @Override
    public void failure() {
        Toast.makeText(this, "Address not found", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        if (mMap != null) {
            switch (v.getId()) {
                case R.id.ivMapHybrid:
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
                case R.id.ivMapSatellite:
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                case R.id.ivMapTerrain:
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    break;
            }
        }
    }
}
