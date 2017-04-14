package com.example.vasuchand.lbb;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class MainActivity extends AppCompatActivity  {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
      //  findPlace(this.findViewById(android.R.id.content).getRootView());

//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getParentFragment().getChildFragmentManager().findFragmentById(R.id.map);
//        autocompleteFragment.setOnPlaceSelectedListener(this);
//        autocompleteFragment.setHint("Search a Location");
        //autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);

    }

    public void findPlace(View view) {
        try {
            AutocompleteFilter typeFilte = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setCountry("IN").build();
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, 1);

        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);

                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber() + " " +place.getRating() +" " +place.getAttributions() + " "+place.getWebsiteUri());

//                ((TextView) findViewById(R.id.searched_address))
//                        .setText(place.getName()+",\n"+
//                                place.getAddress() +"\n" + place.getPhoneNumber());
                System.out.println(place.getPhoneNumber()+ " sdsdfd");
                //System.out.println(place.getWebsiteUri() + " wfwefe");

                Intent intent = new Intent(MainActivity.this,
                        GoogleMap   .class);
                intent.putExtra("latitude",place.getLatLng().latitude);
                intent.putExtra("longitute",place.getLatLng().longitude);
                intent.putExtra("name",place.getName());
                intent.putExtra("address",place.getAddress());
                if(!place.getPhoneNumber().equals("-1.0"))
                intent.putExtra("phone",place.getPhoneNumber());
                else
                    intent.putExtra("phone","null");
                if(place.getWebsiteUri()!=null)
                    intent.putExtra("website",place.getWebsiteUri());
                else
                    intent.putExtra("website","null");
                startActivity(intent);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    }


}
