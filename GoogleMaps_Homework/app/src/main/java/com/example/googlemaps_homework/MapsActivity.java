package com.example.googlemaps_homework;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static android.os.Build.VERSION_CODES.P;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final static int REQUEST_LOCATION=90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ZoomControls zoom = (ZoomControls) findViewById(R.id.zoom);
        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        zoom.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        final Button btn_TypeMap = (Button) findViewById(R.id.btn_Type);
        btn_TypeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    btn_TypeMap.setText("Normal");
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    btn_TypeMap.setText("SAT");
                }
            }
        });

        Button btnFind = (Button) findViewById(R.id.btn_Find);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        EditText etLocation = (EditText) findViewById(R.id.et_Location);
                        String location = etLocation.getText().toString();
                        List<Address> addressList = null;



                        if (location != null && !location.equals("")) {
                            Geocoder geocoder = new Geocoder(MapsActivity.this);
                            try {
                                addressList = geocoder.getFromLocationName(location, 5);

                                if (addressList != null) {
                                    for (int i = 0; i < addressList.size(); i++) {

                                        Address address = addressList.get(i);

                                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                        mMap.addMarker(new MarkerOptions().position(latLng).title(location+" "+ address.getPostalCode()).icon(BitmapDescriptorFactory.fromResource(R.drawable.images1)));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                                    }
                                } else {
                                    return;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();

                            }

                        } else {
                            return;
                        }

            }
        });
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

        // Add a marker in Sydney and move the camera
        LatLng Turkey = new LatLng(38, 38);
        mMap.addMarker(new MarkerOptions().position(Turkey).title("Türkiye").icon(BitmapDescriptorFactory.fromResource(R.drawable.images1)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Turkey));

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }else{
            if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_LOCATION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    mMap.setMyLocationEnabled(true);
                }
            }else{
                Toast.makeText(getApplicationContext(),"Users location not found..",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
