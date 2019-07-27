package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import android.location.Geocoder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences myPref;
    private String carBrand;
    private String city;
    private String sateliteView;

    class CustomInfoWindowAdapter implements InfoWindowAdapter {

        private final View mWindow;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {

            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {

            render(marker, mWindow);
            return mWindow;
        }


        private void render(Marker marker, View view) {
            int badge;
            // Use the equals() method on a Marker to check for equals.  Do not use ==.
            if(carBrand.equals("Mercedes")){
                badge = R.drawable.mercedesbenzlogo464x300;
                ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
            }
            else if(carBrand.equals("Toyota")){
                badge = R.drawable.toyotalogo;
                ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
            }
            else if(carBrand.equals("Audi")){
                badge = R.drawable.audilogo;
                ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
            }else if(carBrand.equals("Honda")){
                badge = R.drawable.hondalogo;
                ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
            }else if(carBrand.equals("Lexus")){
                badge = R.drawable.lexuslogo;
                ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
            }


           String title = marker.getTitle();
           TextView titleUi = (view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi =  view.findViewById(R.id.snippet);

            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
               snippetText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, snippet.length(), 0);
              // snippetText.setSpan(new ForegroundColorSpan(Color.GRAY), 10, snippet.length(), 0);
               snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        myPref = getSharedPreferences("CarInfo", MODE_PRIVATE);
        carBrand = myPref.getString("CarBrand","");
        Intent intent = getIntent();
        sateliteView =intent.getStringExtra("sateliteView");

        city = intent.getStringExtra("city");
        Toast.makeText(this,
                carBrand+" and "+city+" is selected", Toast.LENGTH_SHORT).show();
       // Toast.makeText(this,
                //sateliteView+" is selected", Toast.LENGTH_SHORT).show();

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

        //set position
        LatLng downtownToronto = new LatLng(43.6541737, -79.3808116);
        LatLng scarboroughToronto = new LatLng(43.773077,-79.257774);
        LatLng NorthYork = new LatLng(43.7708175,-79.4132998);
        LatLng Missisauga = new LatLng(43.5927596,-79.6434366);


        if(carBrand.equals("Mercedes")) {

            if(city.equals("Downtown Toronto")){

                if(sateliteView.equals("Yes"))
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
                LatLng mercedesDowntownShowroom = new LatLng(43.661072,-79.356361);
                LatLng mercedesMidtownShowroom = new LatLng(43.713303,-79.361488);

                String mercedesDowntown = String.format("Address: 761 Dundas St E, \nToronto, ON M5A 4N5\nPhone: (416)-785-5653");

                mMap.addMarker(new MarkerOptions().position(mercedesDowntownShowroom)
                        .title("Mercedes-Benz Downtown Toronto")
                        .snippet(mercedesDowntown)
                        );
                final Geocoder coder = new Geocoder(getApplicationContext());
                String locInfo="";

                try {

                    List<Address> geocodeResults = coder.getFromLocationName("Mercedes-Benz Midtown Toronto", 1);

                    Iterator<Address> locations = geocodeResults.iterator();


                    while (locations.hasNext()) {

                        Address loc = locations.next();

                        //code for address object
                        String pName = loc.getLocality();
                        String featureName = loc.getFeatureName();
                        String postalCode = loc.getPostalCode();
                        String number = loc.getPhone();
                        String road = loc.getThoroughfare();

                        locInfo += String.format("%s %s , \n%s, %s\nPhone: %s ", featureName, road, pName,postalCode,number );


                    }


                } catch (IOException e) {
                    Log.e("GeoAddress", "Failed to get location info", e);
                }


                mMap.addMarker(new MarkerOptions().position(mercedesMidtownShowroom)
                        .title("Mercedes-Benz Midtown")
                        .snippet("Address: "+locInfo)
                );


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(downtownToronto, 13));
            }else if(city.equals("Scarborough"))
            {

            }

        }
        else if(carBrand.equals("Toyota")) {

            if(city.equals("Scarborough")){

                if(sateliteView.equals("Yes"))
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
                LatLng toyotaScarboroughShowroom = new LatLng(43.7262233,-79.2938464);


                String toyotaScarborough = String.format("Address: 1897 Eglinton Avenue East \nToronto, ON M1L 2L9\nPhone: (647) 435-6078");

                mMap.addMarker(new MarkerOptions().position(toyotaScarboroughShowroom)
                        .title("Scarborough Toyota")
                        .snippet(toyotaScarborough)
                );

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scarboroughToronto, 13));
            }
        }
        else if(carBrand.equals("Audi")){
            if(city.equals("North York")){
                if(sateliteView.equals("Yes"))
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());


                LatLng audiMidtownShowroom = new LatLng(43.770057,-79.3351114);
                String audiNorthYork = String.format("Address: 175 Yorkland Blvd, \n North York, , ON M2J 4R2\nPhone: (416) 291-2212");

                mMap.addMarker(new MarkerOptions().position(audiMidtownShowroom)
                        .title("Audi Midtown Toronto")
                        .snippet(audiNorthYork)
                );

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NorthYork, 13));
            }
        }
        else if(carBrand.equals("Honda")){
            if(city.equals("Mississauga")){
                if(sateliteView.equals("Yes"))
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());


                LatLng readyHondaShowroom = new LatLng(43.6068156,-79.5865025);
                LatLng meadowvaleShowroom = new LatLng(43.58425,-79.740098);


                String readyHonda = String.format("Address: 230 Dundas St E, \n Mississauga, ON L5A 1W9\nPhone: (416) 291-2212");
                String meadowvaleHonda = String.format("Address: 2210 Battleford Rd, \n Mississauga, ON L5N 3K6\nPhone: (416) 291-2212");


                mMap.addMarker(new MarkerOptions().position(readyHondaShowroom)
                        .title("Ready Honda")
                        .snippet(readyHonda)
                );

                mMap.addMarker(new MarkerOptions().position(meadowvaleShowroom)
                                .title("Meadowvale Honda")
                                .snippet(meadowvaleHonda));



                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Missisauga, 13));
            }

        }


    }
}
