package com.MillionaireGPS.Maps;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.MillionaireGPS.Location;
import com.MillionaireGPS.R;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private Polyline polyline;
    private Marker m1;
    private Marker m2;
    private boolean isLastLocation, mLocationPermissionGranted = false;
    private String visitorName;
    private TextView txtVisitorName;
    private static final int LOCATION_PREMISSION_REQUEST_CODE = 100;
    private Location lastLocation;
    private List<Location> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }

        Intent intent = getIntent();
        isLastLocation = getIntent().getBooleanExtra("isLastLocation", true);
        if (isLastLocation){
            lastLocation = intent.getParcelableExtra("lastLocation");
        } else {
            locationList = intent.getParcelableArrayListExtra("locationList");
        }
        visitorName = getIntent().getStringExtra("VisitorName");
         txtVisitorName.setText(visitorName);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (googleMap != null) {
            getLastPermissions();
          /*  if (isLastLocation) {
                setLastLocation(getLocation().get(4));
            } else {
                setListLocation();
            }*/
        }
    }

    private void initView() {
        txtVisitorName = findViewById(R.id.txtVisitorName);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void DrawLine() {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(m1.getPosition(), m2.getPosition());
        polylineOptions.color(getResources().getColor(R.color.colorBlue));
        polylineOptions.width(5f);
        polyline = googleMap.addPolyline(polylineOptions);
    }

    private void AddLine(LatLng latLng1, LatLng latLng2) {
        m1 = googleMap.addMarker(new MarkerOptions().position(latLng1).title("موقعیت شما").snippet("نمسدونمم").icon(BitmapDescriptorFactory.fromResource(R.drawable.loc)));
        m2 = googleMap.addMarker(new MarkerOptions().position(latLng2).title("موقعیت شما").snippet("نمسدونمم").icon(BitmapDescriptorFactory.fromResource(R.drawable.loc)));
        DrawLine();
    }


    private void setListLocation(List<Location> location) {
        LatLng latLng1, latLng2 = null;
        for (int i = 0; i < location.size() - 1; i++) {
            Location location1 = location.get(i);
            Location location2 = location.get(i + 1);
            latLng1 = new LatLng(Double.parseDouble(location1.getXLocation()), Double.parseDouble(location1.getYLocation()));
            latLng2 = new LatLng(Double.parseDouble(location2.getXLocation()), Double.parseDouble(location2.getYLocation()));

            AddLine(latLng1, latLng2);
        }
        if (latLng2 != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng2, 10);
            googleMap.animateCamera(cameraUpdate);

        }
    }

    private void setLastLocation(Location location) {
        LatLng latLng = null;
        latLng = new LatLng(Double.parseDouble(location.getXLocation()), Double.parseDouble(location.getYLocation()));
        googleMap.addMarker(new MarkerOptions().position(latLng).title("موقعیت شما").snippet("نمسدونمم")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loc)));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        googleMap.animateCamera(cameraUpdate);
    }

    private void getLastPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission
                            (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PREMISSION_REQUEST_CODE);
            } else {
                if (isLastLocation) {
                    setLastLocation(lastLocation);
                } else {
                    setListLocation(locationList);
                }
            }
        } else {
            if (isLastLocation) {
                setLastLocation(lastLocation);
            } else {
                setListLocation(locationList);
              //  drawLine();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PREMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isLastLocation) {
                        setLastLocation(lastLocation);
                    } else {
                        setListLocation(locationList);
                    }
                }
            }
        }

    }

    private void drawLine() {
        List<LatLng> latLngs = new ArrayList<>();

        LatLng Birjand = new LatLng(32.867128, 59.221670);
        LatLng Mashhad = new LatLng(36.289907, 59.618287);
        LatLng Isfahan = new LatLng(32.660477, 51.668476);
        LatLng Tehran = new LatLng(35.694502, 51.390320);
        LatLng Tabriz = new LatLng(38.093796, 46.275176);
        latLngs.add(Birjand);
        latLngs.add(Mashhad);
        latLngs.add(Isfahan);
        latLngs.add(Tehran);
        latLngs.add(Tabriz);
        PolylineOptions polylineOptions = new PolylineOptions();
        for (int i = 0; i < latLngs.size(); i++) {
            polylineOptions.add(latLngs.get(i));
        }
        polylineOptions.add(latLngs.get(0));
        polylineOptions.clickable(true);

        polyline = googleMap.addPolyline(polylineOptions);

        googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
            }
        });
    }

}
