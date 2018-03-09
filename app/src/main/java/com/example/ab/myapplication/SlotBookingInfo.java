package com.example.ab.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SlotBookingInfo extends AppCompatActivity {
    String selectedSlotId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_info);
        selectedSlotId = getIntent().getStringExtra(ParkingSlots.SLOT_ID);
    }
}
