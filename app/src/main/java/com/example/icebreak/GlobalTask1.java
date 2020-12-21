package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;



public class GlobalTask1 extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        Task,
        View.OnClickListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location currentLocation;
    private Marker currentLocationMarker;
    private static final int REQUEST_USER_LOCATION_CODE = 99;
    private boolean isRandomLocationCreated;
    private LatLng randomLocation;
    private User currentUser;

    private static boolean taskSuccessful = false;
    private Timer timer;
    private int seconds;
    private Button btnScoreboard, btnGame, btnGoBack;


    // Buttonlara listener eklenicek,   Hedefe ulaşma kontrol edilecek/ her pozisyon değiştiğinde.
    //  Hedefe ulaşıldıktan sonra sonra puan verme yapılacak. timer kısmı yapılacak.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_task1);
        currentUser = UserTab.userClass;
        isRandomLocationCreated = false;

        btnScoreboard = (Button) this.findViewById(R.id.btnScoreboard);
        btnGame = (Button) this.findViewById(R.id.btnGame);
        btnGoBack = (Button) this.findViewById(R.id.btnGoBack);

        btnGoBack.setOnClickListener(this);
        btnGame.setOnClickListener(this);
        btnScoreboard.setOnClickListener(this);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.GlobalTask1_map);
        mapFragment.getMapAsync(this);
    }


    public void onClick(View v)
    {
        int id = v.getId();

        switch(id)
        {
            case R.id.btnGame:

            case R.id.btnGoBack:
                // TODO GAME SCREEN FOR OUTDOOR EVENT taskOver çğır
                break;

            case R.id.btnScoreboard:
                Intent intent = new Intent(GlobalTask1.this, OutDoorScoreBoard.class);
                startActivity(intent);
                break;
        }


    }

    @Override
    public void setAndStartTimer(int seconds) {
        this.seconds = seconds;
        timer = new Timer();
        timer.schedule(new TaskTimer(), seconds*1000);

    }

    @Override
    public Timer getTime() {
        return timer;
    }

    @Override
    public String getTaskText() {
        return "Go to the marker!";
    }

    @Override
    public void taskOver() {
        timer.cancel();

        if(taskSuccessful)
        {
            currentUser.setCurrentPoint(currentUser.getCurrentPoint() + 1);
            // TODO NOTİFİCATİON
        }
        else
        {
            //TODO NOTİFİCATİON
        }
    }

    public void createRandomLocation()
    {
        double longitude = currentLocation.getLongitude();
        double latitude = currentLocation.getLatitude();

        double distanceR = 0.003;
        double degree = (int)(Math.random()*360);
        double randomLat = latitude + distanceR*Math.sin(degree/(2*Math.PI));
        double randomLng = longitude + distanceR*Math.cos(degree/(2*Math.PI));

        randomLocation = new LatLng(randomLat,randomLng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(randomLocation);
        markerOptions.title("Go Here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        currentLocationMarker = mMap.addMarker(new MarkerOptions().position(randomLocation).title("Go Here"));

    }

    public class TaskTimer extends TimerTask {

        @Override
        public void run()
        {
            taskOver();
        }
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

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {

            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);

        }

    }

    public boolean checkUserLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_USER_LOCATION_CODE);
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_USER_LOCATION_CODE);
            return false;
        }
        else
            return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_USER_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                    {
                        if(googleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }

                        mMap.setMyLocationEnabled(true);
                    }
                    else
                    {
                        Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
        }

    }

    protected  synchronized  void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation = location;
        if(currentLocationMarker != null)
        {
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));

        if(googleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

        if(!isRandomLocationCreated)
        {
            createRandomLocation();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(randomLocation));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            isRandomLocationCreated = true;
        }

        // Location check for arriving the target.

        double differenceLatitude = location.getLatitude() - randomLocation.latitude;
        double differenceLongitude = location.getLongitude() - randomLocation.longitude;

        double differenceR = differenceLatitude+ differenceLongitude;

        if(differenceR < 0.00007)
        {
            taskSuccessful = true;
            taskOver();
        }



    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}