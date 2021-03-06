package com.example.ab.myapplication.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ab.myapplication.User;
import com.example.ab.myapplication.constants.Constants;

import org.json.JSONObject;

import java.util.HashMap;

public class BookingTask extends AsyncTask<Void, Void, Boolean> {

    private final int startTime;
    private final int endTime;
    private final int startMinute;
    private final int endMinute;
    Context contex;
    String blockId;
    private Response.Listener successListener;
    boolean result = false;

    public BookingTask(String blockId, int startHour, int endHour, int startMinute, int endMinute, Context contex, Response.Listener successListener) {
        this.blockId = blockId;
        this.startTime = startHour;
        this.endTime = endHour;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
        this.contex = contex;
        this.successListener = successListener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(contex);
            String url = Constants.SERVER_URL + "/book/";
            HashMap<String, String> paramsBody = new HashMap<>();
            paramsBody.put("blockId", blockId);
            paramsBody.put("startHour", "" + startTime);
            paramsBody.put("endHour", "" + endTime);
            paramsBody.put("startMinute", "" + startMinute);
            paramsBody.put("endMinute", "" + endMinute);
            paramsBody.put("email", "" + User.getCurrentUser().email);
            JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(paramsBody), successListener, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error while logging in");
                }
            });
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}