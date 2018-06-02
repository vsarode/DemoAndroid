package com.example.ab.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.ab.myapplication.asyncTask.AllBlocksInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParkingSlots extends AppCompatActivity implements View.OnClickListener {
    Button[] slotButtons = new Button[4];
    Button bookSlotButton, viewSlotInfoButton;
    Button selectedSlot;
    Map<Button, String> buttonBlockToIdMap = new HashMap<>();
    Map<String, Button> buttonIdToBlockMap = new HashMap<>();
    public static String SLOT_ID = "slot_id";
    int selectedColor = Color.GRAY;
    int defaultColor = Color.GREEN;
    int colorBooked = Color.YELLOW;
    Map<Button, Boolean> buttonFreeStatus = new HashMap<>();

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
        initializeBlockBookings();
    }

    private void initializeBlockBookings() {
        final Context myContext = this;
        try {
            new AllBlocksInfo(myContext, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    try {
                        JSONArray parkingBlocks = new JSONObject(response.toString()).getJSONObject("responseData").getJSONArray("parkingBlocks");
                        for (int i = 0; i < parkingBlocks.length(); i++) {
                            JSONObject block = parkingBlocks.getJSONObject(i);
                            boolean isFree = block.getBoolean("isFree");
                            String blockId = block.getString("blockCode");
                            Button blockBtn = buttonIdToBlockMap.get(blockId);
                            buttonFreeStatus.put(blockBtn, isFree);
                            if (!isFree && blockBtn != null) {
                                blockBtn.setBackgroundColor(colorBooked);
                            }
                        }
                    } catch (JSONException e) {
                        Toast.makeText(myContext, "Failed to parse response", Toast.LENGTH_SHORT).show();
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeBlockMap() {
        for (int i = 0; i < slotButtons.length; i++) {
            buttonBlockToIdMap.put(slotButtons[i], "B" + i);
            buttonIdToBlockMap.put("B" + i, slotButtons[i]);
            slotButtons[i].setBackgroundColor(defaultColor);
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
            boolean isFree = buttonFreeStatus.get(selectedSlot);
            if (isFree) {
                selectedSlot.setBackgroundColor(defaultColor);
            } else {
                selectedSlot.setBackgroundColor(colorBooked);
            }
        }
        selectedSlot = (Button) view;
        selectedSlot.setBackgroundColor(selectedColor);
    }

    private void showNoSelectionMessage() {
        Toast.makeText(this, "Please select one of the slot", Toast.LENGTH_SHORT).show();
    }

    private void handleBooking() {
        startNewActivity(Book.class);
    }

    private void handleViewDetails() {
        startNewActivity(SlotBookingInfo.class);
    }

    private void startNewActivity(Class nextActivity) {
        String slotSelected = buttonBlockToIdMap.get(selectedSlot);
        Intent intent = new Intent(this, nextActivity);
        intent.putExtra(SLOT_ID, slotSelected);
        startActivity(intent);
    }

    private void initializeSLots() {
        slotButtons[0] = (Button) findViewById(R.id.bt1);
        slotButtons[1] = (Button) findViewById(R.id.bt2);
        slotButtons[2] = (Button) findViewById(R.id.bt3);
        slotButtons[3] = (Button) findViewById(R.id.bt4);

    }
}
