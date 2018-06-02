package com.example.ab.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.ab.myapplication.asyncTask.BookingTask;

import org.json.JSONException;
import org.json.JSONObject;

public class Book extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE = 1;
    TimePicker startTimePicker, endTimePicker;
    Button confirmBooking;
    String selectedSlotId;

    static double lattitude;
    static double longitude;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        startTimePicker = (TimePicker) findViewById(R.id.tpStart);
        endTimePicker = (TimePicker) findViewById(R.id.tpEnd);
        confirmBooking = (Button) findViewById(R.id.bt_confirm_booking);
        confirmBooking.setOnClickListener(this);
        selectedSlotId = getIntent().getStringExtra(ParkingSlots.SLOT_ID);
        startTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);
        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        getLocation();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                lattitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {
                Toast.makeText(this, "Couldn't fetch location, sorry", Toast.LENGTH_LONG).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Booking your slot..", Toast.LENGTH_SHORT).show();
        int startHour = startTimePicker.getHour();
        int startMin = startTimePicker.getMinute();
        int endHour = endTimePicker.getHour();
        int endMin = endTimePicker.getMinute();
        final Context myContext = this;
        try {
            new BookingTask(selectedSlotId, startHour, endHour, startMin, endMin, this, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    try {
                        JSONObject bookingResponse = new JSONObject(response.toString());
                        String message = bookingResponse.getString("message");
                        if (message.equals("Operation Successful")) {
                            handleBookingSuccess();
                        } else {
                            Toast.makeText(myContext, "Could not book slot", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (JSONException e) {
                        System.out.println("Failed to parse response object");
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleBookingSuccess() {
        new AlertDialog.Builder(this)
                .setTitle("Booking successful")
                .setMessage("do you want to start Navigation")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        NavigateToDestination();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void NavigateToDestination() {
        String dlattitude = "18.520430";
        String dlongitude = "73.856744";
        Double slattitude = lattitude;
        Double slongitude = longitude;
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=" + slattitude + "," + slongitude + "&daddr=" + dlattitude + "," + dlongitude));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getLocation();
    }
}
