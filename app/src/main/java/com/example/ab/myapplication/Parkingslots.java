package com.example.ab.myapplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class Parkingslots extends AppCompatActivity implements View.OnClickListener {

    Button slots1;
    Button slots2;
    Button slots3;
    Button slots4;
    Button slots5;
    Button slots6;
    Button slots7;
    Button slots8;
    Button slots9;

    TimePicker t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkingslots);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slots1 = (Button) findViewById(R.id.bt1);
        slots2 = (Button) findViewById(R.id.bt2);
        slots3 = (Button) findViewById(R.id.bt3);
        slots4 = (Button) findViewById(R.id.bt4);
        slots5 = (Button) findViewById(R.id.bt5);
        slots6 = (Button) findViewById(R.id.bt6);
        slots7 = (Button) findViewById(R.id.bt7);
        slots8 = (Button) findViewById(R.id.bt8);
        slots9 = (Button) findViewById(R.id.bt9);
        t = (TimePicker) findViewById(R.id.tp);
        t.setEnabled(false);
        slots1.setOnClickListener(this);
        slots2.setOnClickListener(this);
        slots3.setOnClickListener(this);
        slots4.setOnClickListener(this);
        slots5.setOnClickListener(this);
        slots6.setOnClickListener(this);
        slots7.setOnClickListener(this);
        slots8.setOnClickListener(this);
        slots9.setOnClickListener(this);
        final Context iam = this;
        t.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                Toast.makeText(iam, "Current time  " + hour + ":" + minute, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String resourceName = view.getResources().getResourceName(view.getId());
        Toast.makeText(this, "Clicked on : " + resourceName, Toast.LENGTH_SHORT).show();
        t.setEnabled(true);

    }
}
