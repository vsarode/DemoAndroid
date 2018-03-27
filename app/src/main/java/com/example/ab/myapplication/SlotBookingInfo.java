package com.example.ab.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.ab.myapplication.asyncTask.BookingInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SlotBookingInfo extends AppCompatActivity {
    String selectedSlotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_info);
        selectedSlotId = getIntent().getStringExtra(ParkingSlots.SLOT_ID);
        loadBlockInfo();
    }

    private void loadBlockInfo() {
        final Context myContext = this;
        try {
            new BookingInfo(selectedSlotId, myContext, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    try {
                        JSONObject parkingBlock = new JSONObject(response.toString());
                        JSONArray bookings = parkingBlock.getJSONObject("responseData").getJSONObject("parkingBlock").getJSONArray("bookings");
                        for (int i = 0; i < bookings.length(); i++) {
                            TableLayout ll = (TableLayout) findViewById(R.id.tableLayout);
                            JSONObject block = bookings.getJSONObject(i);
                            int startHour, startMin, endMin, endHour;
                            startHour = block.getInt("startHour");
                            endHour = block.getInt("endHour");
                            startMin = block.getInt("startMinute");
                            endMin = block.getInt("endMinute");
                            TableRow row = new TableRow(myContext);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            TextView start, end;
                            row.setLayoutParams(lp);
                            start = new TextView(myContext);
                            end = new TextView(myContext);
                            start.setText(startHour + ":" + startMin);
                            end.setText(endHour + ":" + endMin);
                            row.addView(start);
                            row.addView(end);
                            ll.addView(row, i);
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
}