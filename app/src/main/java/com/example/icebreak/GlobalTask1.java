package com.example.icebreak;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Timer;
import java.util.TimerTask;


public class GlobalTask1 extends AppCompatActivity implements OnMapReadyCallback,
        Task,
        View.OnClickListener {

    private static final String TAG = "GlobalTask1";
    private static final int LOCATION_REQUEST_CODE = 99;
    private GoogleMap mMap;
    private Location currentLocation;
    private LatLng randomLocation;
    private Marker randomLocationMarker;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback = new LocationCallback()
    {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if(locationResult == null)
            {
                return;
            }
            for(Location location: locationResult.getLocations())
            {
                currentLocation = location;
                Log.d(TAG, "onLocationResult: " + location.toString());
                if(!isRandomLocationCreated)
                {
                    createRandomLocation();
                    isRandomLocationCreated = true;
                }
                double differenceLatitude = Math.abs(location.getLatitude() - randomLocation.latitude);
                double differenceLongitude = Math.abs(location.getLongitude() - randomLocation.longitude);

                double differenceR = differenceLatitude+ differenceLongitude;

                if(differenceR < 0.00009)
                {
                    taskSuccessful = true;
                    taskOver();
                }
            }
        }
    };


    private static boolean taskSuccessful = false;
    private TextView remainingTime;
    private Button btnScoreboard, btnGame, btnGoBack;
    private User currentUser = UserTab.userClass;
    private CountDownTimer countDownTimer;

    private boolean isRandomLocationCreated = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_task1);
        isRandomLocationCreated = false;

        // XML files
        btnScoreboard = this.findViewById(R.id.btnScoreboard);
        btnGame = this.findViewById(R.id.btnGame);
        btnGoBack = this.findViewById(R.id.btnGoBack);
        remainingTime = this.findViewById(R.id.timer);

        btnGoBack.setOnClickListener(this);
        btnGame.setOnClickListener(this);
        btnScoreboard.setOnClickListener(this);

        // Other instances
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Start the timer
        setAndStartTimer(300);





        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.GlobalTask1_map);
        mapFragment.getMapAsync(this);


    }

    public void onClick(View v) {
        int id = v.getId();
        Intent intent;

        switch (id) {
            case R.id.btnGame:
            case R.id.btnGoBack:
                intent = new Intent(GlobalTask1.this, OutdoorEventMainActivity.class);
                startActivity(intent);
                break;

            case R.id.btnScoreboard:
                intent = new Intent(GlobalTask1.this, OutDoorScoreBoard.class);
                startActivity(intent);
                break;
        }


    }

    @Override
    public void setAndStartTimer(int seconds) {
        countDownTimer = new CountDownTimer(seconds*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime.setText("Remaining: " + String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                taskOver();
            }
        };

        countDownTimer.start();

    }

    @Override
    public Timer getTime() {
        return null;
    }

    @Override
    public String getTaskText() {
        return "Go to the marker!";
    }

    @Override
    public void taskOver() {

        if (taskSuccessful)
        {
            if(countDownTimer != null)
                countDownTimer.cancel();

            //current user puan alınımı eklenecek sorun çıkardığı için comment atıldı
            //currentUser.setCurrentPoint(currentUser.getCurrentPoint() + 1);
            UserTab.userClass.setCurrentPoint(UserTab.userClass.getCurrentPoint() + 10);


            stopLocationUpdates();
            openWinDialog();

        }
        else
        {
            if(countDownTimer != null)
                countDownTimer.cancel();
            stopLocationUpdates();
            openLoseDialog();
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

    }

    public void createRandomLocation()
    {
        Location cLocation = currentLocation;
        double longitude = cLocation.getLongitude();
        double latitude = cLocation.getLatitude();

        double distanceR = 0.0015;
        double degree = (int)(Math.random()*360);
        double randomLat = latitude + distanceR*Math.sin(degree/(2*Math.PI));
        double randomLng = longitude + distanceR*Math.cos(degree/(2*Math.PI));

        randomLocation = new LatLng(randomLat,randomLng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(randomLocation);
        markerOptions.title("Go Here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        randomLocationMarker = mMap.addMarker(new MarkerOptions().position(randomLocation).title("Go Here"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(randomLocation, 17);
        mMap.animateCamera(cameraUpdate);

    }

    private void checkSettingsAndStartLocationUpdates()
    {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();

        SettingsClient client = LocationServices.getSettingsClient(this);

        com.google.android.gms.tasks.Task<LocationSettingsResponse> locationSettingsResponseTask= client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // Settings of the device are satisfied, start location updates
                startLocationUpdates();
            }
        });

        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException)
                {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(GlobalTask1.this, 100);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates()
    {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates()
    {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    private void openWinDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_winnotification);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button OKButton = dialog.findViewById(R.id.OKButton);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(GlobalTask1.this, OutdoorEventMainActivity.class);
                startActivity(intent);
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(GlobalTask1.this, OutdoorEventMainActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    private void openLoseDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_losenotification);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button OKButton = dialog.findViewById(R.id.OKButton);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(GlobalTask1.this, OutdoorEventMainActivity.class);
                startActivity(intent);
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(GlobalTask1.this, OutdoorEventMainActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkSettingsAndStartLocationUpdates();
        } else {
            askLocationPermission();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        com.google.android.gms.tasks.Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null)
                {
                    Log.d(TAG, "onSuccess: "+ location.toString());
                    Log.d(TAG, "onSuccess: " + location.getLongitude());
                    Log.d(TAG, "onSuccess: " + location.getLatitude());
                }
                else
                {
                    Log.d(TAG, "onSuccess: Location was null");
                }
            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.getLocalizedMessage() );
            }
        });
    }

    private void askLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Log.d(TAG, "askLocationPermission: Alert no location permission");
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},  LOCATION_REQUEST_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},  LOCATION_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_REQUEST_CODE)
        {
            if(grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //Permission granted
                checkSettingsAndStartLocationUpdates();
            }
            else
            {
                //Permission not granted
                askLocationPermission();
            }
        }
    }
}