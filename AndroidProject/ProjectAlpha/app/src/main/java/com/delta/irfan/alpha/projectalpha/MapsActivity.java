package com.delta.irfan.alpha.projectalpha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.delta.irfan.alpha.R;
import com.delta.irfan.alpha.dataaccess.CustomerBusinessLogic;
import com.delta.irfan.alpha.location.GPSTracker;
//import com.delta.irfan.alpha.projectalpha.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String phone;
    Double Lng = 0.0;
    Double Lat = 0.0;
    CustomerBusinessLogic customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        customer = new CustomerBusinessLogic(this);
        phone = intent.getStringExtra("phone");
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GPSTracker gpsTracker = new GPSTracker(this);
        gpsTracker.getLongitude();
        this.retrieveLatLng();

        if ((Lat.equals(0.0) & Lng.equals(0.0))
                | (Lat == null & Lng == null)) {
            Lat = gpsTracker.getLatitude();
            Lng = gpsTracker.getLongitude();
        }


        // Add a marker in Sydney and move the camera

        LatLng currLocation = new LatLng(Lat, Lng);
//        mMap.addMarker(new MarkerOptions().position(currLocation).title("Marker in Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));

        LatLngBounds currLocationBounds = new LatLngBounds(
                currLocation, currLocation);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocationBounds.getCenter(), 50));


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            CustomerBusinessLogic.FeedEntry feedEntry = new CustomerBusinessLogic.FeedEntry();


            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);

                updateLatLng(new Double(arg0.getPosition().latitude),
                        new Double(arg0.getPosition().longitude));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
            }


            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });

//Don't forget to Set draggable(true) to marker, if this not set marker does not drag.

        mMap.addMarker(new MarkerOptions().position(currLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_my_location))
                .draggable(true));
    }
    private void retrieveLatLng(){
        CustomerBusinessLogic customer = new CustomerBusinessLogic(this);
        CustomerBusinessLogic.FeedEntry feedEntry = new CustomerBusinessLogic.FeedEntry();
        feedEntry = customer.readCustomer(phone);

        if (feedEntry.getLat() == null | feedEntry.getLng() == null) {
            Lat = 0.0;
            Lng = 0.0;
        }
        else {
            Lat = Double.parseDouble(feedEntry.getLat());
            Lng = Double.parseDouble(feedEntry.getLng());
        }
    }
    private void updateLatLng(Double Lat, Double Lng){
        CustomerBusinessLogic customer = new CustomerBusinessLogic(this);
        CustomerBusinessLogic.FeedEntry feedEntry = new CustomerBusinessLogic.FeedEntry();
        feedEntry.setLat(Lat.toString());
        feedEntry.setLng(Lng.toString());
        feedEntry.setPhone(phone);

        customer.updateCustomer(feedEntry);

    }
}
