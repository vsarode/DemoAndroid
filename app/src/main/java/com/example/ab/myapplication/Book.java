package com.example.ab.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.ab.myapplication.asyncTask.BookingTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Book extends AppCompatActivity implements View.OnClickListener {

    TimePicker startTimePicker, endTimePicker;
    Button confirmBooking;
    String selectedSlotId;

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
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Booking your slot..", Toast.LENGTH_SHORT).show();
        int start = startTimePicker.getHour();
        int end = startTimePicker.getMinute();
//        try {
//            new BookingTask(selectedSlotId,start,end, this, new Response.Listener() {
//                @Override
//                public void onResponse(Object response) {
//                    try {
//                        JSONObject loginResponse = new JSONObject(response.toString());
//                    } catch (JSONException e) {
//                        System.out.println("Failed to parse response object");
//                    }
//                }
//            }).execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String dlattitude = "18.520430";
        String dlongitude = "73.856744";

        String slattitude = "17.902781";
        String slongitude = "74.081415";

        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=" + slattitude + "," + slongitude + "&daddr=" + dlattitude + "," + dlongitude));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);

    }
}
