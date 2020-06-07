package com.budyfriend.curentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurentActivity extends AppCompatActivity implements com.google.android.gms.location.LocationListener{
    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curent);

        btLocation = findViewById(R.id.btnLocation);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(CurentActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(CurentActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Locale indo = new Locale("in", "ID");
                        Geocoder geocoder = new Geocoder(CurentActivity.this, indo);
//                    initial address
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        textView1.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude : </b></font>" +
                                addresses.get(0).getLatitude()));

                        textView2.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude : </b></font>" +
                                addresses.get(0).getLongitude()));


                        textView3.setText(Html.fromHtml("<font color='#6200EE'><b>Country Name : </b></font>" +
                                addresses.get(0).getCountryName()));

                        textView4.setText(Html.fromHtml("<font color='#6200EE'><b>Locality : </b></font>" +
                                addresses.get(0).getLocality()));

                        textView5.setText(Html.fromHtml("<font color='#6200EE'><b>Address : </b></font>" +
                                addresses.get(0).getAddressLine(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Locale indo = new Locale("in", "ID");
            Geocoder geocoder = new Geocoder(CurentActivity.this, indo);
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            textView1.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude : </b></font>" +
                    addresses.get(0).getLatitude()));

            textView2.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude : </b></font>" +
                    addresses.get(0).getLongitude()));


            textView3.setText(Html.fromHtml("<font color='#6200EE'><b>Country Name : </b></font>" +
                    addresses.get(0).getCountryName()));

            textView4.setText(Html.fromHtml("<font color='#6200EE'><b>Locality : </b></font>" +
                    addresses.get(0).getLocality()));

            textView5.setText(Html.fromHtml("<font color='#6200EE'><b>Address : </b></font>" +
                    addresses.get(0).getAddressLine(0)));


            Toast.makeText(CurentActivity.this,"Seved",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}