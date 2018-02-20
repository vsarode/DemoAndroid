package com.example.ab.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.ab.myapplication.asyncTask.UserSignUp;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    EditText name;
    EditText email;
    EditText address;
    EditText mobile;
    EditText pwd;
    EditText confirmpwd;
    Button sign_up;
    private UserSignUp mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = (EditText) findViewById(R.id.txtname);
        email = (EditText) findViewById(R.id.txtemail);
        address = (EditText) findViewById(R.id.txtaddress);
        mobile = (EditText) findViewById(R.id.txtmobile);
        pwd = (EditText) findViewById(R.id.txtpwd);
        confirmpwd = (EditText) findViewById(R.id.txtconfirmpwd);

        sign_up = (Button) findViewById(R.id.btnsign_up);

        sign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mAuthTask != null) {
            return;
        }
        String uname = name.getText().toString();
        String uemail = email.getText().toString();
        String uaddress = address.getText().toString();
        String umobile = mobile.getText().toString();
        String upwd = pwd.getText().toString();
        String ucpwd = confirmpwd.getText().toString();
        final Context thisActivity = this;
        try {
            new UserSignUp(this, uname, uemail, uaddress, umobile, upwd, ucpwd, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    Toast.makeText(thisActivity, "Got success response..", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearFields();
    }

    private void clearFields() {
        name.setText("");
        email.setText("");
        address.setText("");
        mobile.setText("");
        pwd.setText("");
        confirmpwd.setText("");
    }
}
