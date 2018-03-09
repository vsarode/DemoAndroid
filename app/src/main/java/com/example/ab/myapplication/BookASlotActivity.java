package com.example.ab.myapplication;

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

public class BookASlotActivity extends AppCompatActivity implements View.OnClickListener {

    TimePicker startTimePicker, endTimePicker;
    Button confirmBooking;
    String selectedSlotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
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
        Date startDate = new Date();

        startDate.setHours(startTimePicker.getHour());
        startDate.setMinutes(startTimePicker.getMinute());

        Date endDate = new Date();

        endDate.setHours(endTimePicker.getHour());
        endDate.setMinutes(endTimePicker.getMinute());
        startDate.getTime();
        try {
            new BookingTask(selectedSlotId, this, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    try {
                        JSONObject loginResponse = new JSONObject(response.toString());
                    } catch (JSONException e) {
                        System.out.println("Failed to parse response object");
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
