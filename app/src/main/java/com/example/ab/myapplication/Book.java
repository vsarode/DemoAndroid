package com.example.ab.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

        String dlattitude = "18.591395";
        String dlongitude = "74.00380333333332";

        String slattitude = "18.591395";
        String slongitude = "74.00380333333332";

        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=" + slattitude + "," + slongitude + "&daddr=" + dlattitude + "," + dlongitude));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
}
