package com.example.ab.myapplication.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ab.myapplication.constants.Constants;

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String email;
    private final String password;
    Context contex;
    boolean result = false;

    public UserLoginTask(String email, String password, Context contex) {
        this.email = email;
        this.password = password;
        this.contex = contex;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(this.contex, "Logging in...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(contex);
            String url = Constants.SERVER_URL + "/api/login?email=" + email + "&password=" + password;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.contains("true")) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (result) {
            Toast.makeText(contex, "Success...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(contex, "Login Attempt Failed", Toast.LENGTH_SHORT).show();
        }
    }
}