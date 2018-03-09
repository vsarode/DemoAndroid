package com.example.ab.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class ParkingSlots extends AppCompatActivity implements View.OnClickListener {

    Button[] slotButtons = new Button[9];
    Button bookSlotButton, viewSlotInfoButton;
    Button selectedSlot;
    Map<Button, String> buttonBlockIdMap = new HashMap<>();
    public static String SLOT_ID = "slot_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkingslots);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bookSlotButton = (Button) findViewById(R.id.bt_book_slot);
        viewSlotInfoButton = (Button) findViewById(R.id.bt_viewBookigs);

        bookSlotButton.setOnClickListener(this);
        viewSlotInfoButton.setOnClickListener(this);

        initializeSLots();
        setOnClickListener();
        initializeBlockMap();
    }

    private void initializeBlockMap() {
        for (int i = 0; i < slotButtons.length; i++) {
            buttonBlockIdMap.put(slotButtons[i], "" + i);
        }
    }

    private void setOnClickListener() {
        for (int i = 0; i < slotButtons.length; i++) {
            slotButtons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_viewBookigs) {
            if (selectedSlot == null) {
                showNoSelectionMessage();
            }
            handleViewDetails();
            return;
        }

        if (id == R.id.bt_book_slot) {
            if (selectedSlot == null) {
                showNoSelectionMessage();
            }
            handleBooking();
            return;
        }
        if (selectedSlot != null) {
            selectedSlot.setBackgroundColor(Color.YELLOW);
        }
        selectedSlot = (Button) view;
        selectedSlot.setBackgroundColor(Color.GRAY);
    }

    private void showNoSelectionMessage() {
        Toast.makeText(this, "Please select one of the slot", Toast.LENGTH_SHORT).show();
    }

    private void handleBooking() {
        startNewActivity(BookASlotActivity.class);
    }

    private void handleViewDetails() {
        startNewActivity(SlotBookingInfo.class);
    }

    private void startNewActivity(Class nextActivity) {
        String slotSelected = buttonBlockIdMap.get(selectedSlot);
        Intent intent = new Intent(this, nextActivity);
        intent.putExtra(SLOT_ID, slotSelected);
        startActivity(intent);
    }

    private void initializeSLots() {
        slotButtons[0] = (Button) findViewById(R.id.bt1);
        slotButtons[1] = (Button) findViewById(R.id.bt2);
        slotButtons[2] = (Button) findViewById(R.id.bt3);
        slotButtons[3] = (Button) findViewById(R.id.bt4);
        slotButtons[4] = (Button) findViewById(R.id.bt5);
        slotButtons[5] = (Button) findViewById(R.id.bt6);
        slotButtons[6] = (Button) findViewById(R.id.bt7);
        slotButtons[7] = (Button) findViewById(R.id.bt8);
        slotButtons[8] = (Button) findViewById(R.id.bt9);
    }
}
