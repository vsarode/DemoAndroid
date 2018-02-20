package com.example.ab.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Prakingground extends AppCompatActivity  implements View.OnClickListener {
    Button ground1;
    Button ground2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prakingground);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ground1 = (Button) findViewById(R.id.btground1);
        ground2 = (Button) findViewById(R.id.btground2);

        ground1.setOnClickListener(this);
        ground2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,Parkingslots.class);
        startActivity(intent);
    }
}
