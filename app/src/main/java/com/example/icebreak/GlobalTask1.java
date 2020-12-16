package com.example.icebreak;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;



public class GlobalTask1 extends FragmentActivity implements OnMapReadyCallback, Task {

    private GoogleMap mMap;

    private Timer timer;
    private String taskText;
    private boolean taskCompleted;
    private int seconds;
    private LocationListener myLocationListener;
    private LocationManager locationManager;

    public GlobalTask1(String taskText)
    {
        this.taskText = taskText;
        createRandomLocation();
        myLocationListener = new MyLocationListener();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {


            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    11 );
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, myLocationListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_task1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.GlobalTask1);
        mapFragment.getMapAsync(this);
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
        return taskText;
    }

    @Override
    public void taskOver() {
        timer.cancel();
    }

    public void createRandomLocation()
    {
        LatLng randomLocation = new LatLng(0,0);
    }

    public class TaskTimer extends TimerTask {

        @Override
        public void run()
        {
            taskOver();
        }
    }

    public class MyLocationListener implements LocationListener
    {

        @Override
        public void onLocationChanged(Location location)
        {

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}