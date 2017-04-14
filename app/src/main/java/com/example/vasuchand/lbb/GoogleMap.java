package com.example.vasuchand.lbb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Vasu Chand on 4/14/2017.
 */

public class GoogleMap extends AppCompatActivity implements OnMapReadyCallback {
    String title, address,contact,website;
    double latitude,longitude;
    Toolbar toolbar;
    Context context = GoogleMap.this;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        initToolbar();
        t1 = (TextView)findViewById(R.id.contact);
        t2 = (TextView)findViewById(R.id.web);
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0d);
        longitude = intent.getDoubleExtra("longitute",0d);
        title = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        contact = intent.getStringExtra("phone");
        website = intent.getStringExtra("website");
        System.out.println("adsassad" +contact + " " + website) ;

        if(website.equals("null" )||website.isEmpty() ||website.contains(" "))
        {
            t2.setText("Not Available");

        }
        else
            t2.setText(website);


        if(contact.equals("null") || contact.isEmpty() ||contact.contains(" "))
        {

            t1.setText("Not Available");
        }
        else
            t1.setText(contact);


        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap map) {

        LatLng latlng = new LatLng(latitude,longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13));
        map.addMarker( new MarkerOptions()
                .title(title)
                .snippet(address)
                .position(latlng));
    }
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
